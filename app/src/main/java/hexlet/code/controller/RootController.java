package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import io.javalin.http.Context;

import java.util.Map;

public class RootController {
    public static void index(Context ctx) {
        ctx.render("index.jte", Map.of("page", new BasePage()));
    }
}
