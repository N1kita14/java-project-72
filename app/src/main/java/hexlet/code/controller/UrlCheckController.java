package hexlet.code.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hexlet.code.models.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;

public class UrlCheckController {
    public static void create(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id).get().getName();

        try {
            HttpResponse<String> response = Unirest.get(url).asString();
            Integer statusCode = response.getStatus();

            Document doc = Jsoup.parse(response.getBody());
            String title = doc.title();
            String h1 = doc.select("h1").text();
            String description = doc.select("meta[name=description]").attr("content");

            var urlCheck = new UrlCheck(statusCode, title, h1, description, id);

            UrlCheckRepository.save(urlCheck);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Произошла ошибка при проверке страницы");
        }
        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
