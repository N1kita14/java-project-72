package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.models.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var listChecks = UrlCheckRepository.getLastChecks();
        var page = new UrlsPage(urls, listChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Page not found"));
        var urlList = UrlCheckRepository.getEntities(id);
        var page = new UrlPage(url, urlList);
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var name = ctx.formParam("url");

        try {
            // Проверяем, что URL корректен синтаксически
            URL absoluteUrl = new URI(name).toURL();
            String schema = absoluteUrl.toURI().getScheme();
            String authority = absoluteUrl.toURI().getAuthority();

            if (schema == null || authority == null) {
                throw new URISyntaxException(name, "Некорректный формат URL");
            }

            // Проверяем, что домен реально существует
            try {
                var connection = (HttpURLConnection) absoluteUrl.openConnection();
                connection.setRequestMethod("HEAD");
                connection.setConnectTimeout(3000);
                connection.connect();
                int code = connection.getResponseCode();
                if (code >= 400) {
                    ctx.sessionAttribute("flash", "Сайт недоступен или не существует");
                    ctx.redirect(NamedRoutes.rootPath());
                    return;
                }
            } catch (Exception e) {
                ctx.sessionAttribute("flash", "URL недоступен или не существует");
                ctx.redirect(NamedRoutes.rootPath());
                return;
            }

            Url url = new Url(schema + "://" + authority);
            Optional<Url> foundUrl = UrlRepository.findByName(url.getName());

            if (foundUrl.isEmpty()) {
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует");
            }

            ctx.redirect(NamedRoutes.urlsPath());

        } catch (URISyntaxException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath()); // остаться на главной!
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Ошибка при добавлении URL");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }
}
