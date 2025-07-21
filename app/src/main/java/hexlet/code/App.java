package hexlet.code;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
    private static Javalin instance;
    public static void main(String[] args) {
        var app = getApp();
                app.get("/", ctx -> ctx.result("Hello World"));
    }
    public static Javalin getApp() {
        if(instance == null) {
            instance = Javalin.create().start(7000);
        }
        return instance;
    }
}
