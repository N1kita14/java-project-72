package hexlet.code;

import hexlet.code.models.Url;
//import hexlet.code.models.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;
//import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class AppTest {
    //public static Javalin app;
    public static MockWebServer mockServer;
    @BeforeAll
    public static void beginServer() throws IOException {
        mockServer = new MockWebServer();
        Path mockTest = Paths.get("src/test/resources/test.html").toAbsolutePath().normalize();
        MockResponse mockResponse = new MockResponse()
                .setBody(Files.readString(mockTest))
                .setResponseCode(200);
        mockServer.enqueue(mockResponse);
        mockServer.start();
    }

    //@BeforeEach
    //public void setUp() throws Exception {
        //app = App.getApp();
    //}

    @AfterAll
    public static void endServer() throws IOException {
        mockServer.shutdown();
    }

    @Test
    public void testUrlsPage() throws Exception {

        JavalinTest.test(App.getApp(), (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            //assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlPage() throws Exception {

        JavalinTest.test(App.getApp(), (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlCreate() throws SQLException, IOException {
        var url = new Url("https://www.example.com");
        UrlRepository.save(url);
        JavalinTest.test(App.getApp(), (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            //assertThat(response.body().string()).contains("https://www.example.com");
        });
    }
    @Test
    public void testUrlsCreate() throws SQLException, IOException {
        JavalinTest.test(App.getApp(), (server, client) -> {
            String requestBody = "url=https://www.example.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.example.com");
            assertThat(UrlRepository.getEntities().size() == 1);
        });
    }

    @Test
    public void testNotFound() throws SQLException, IOException {
        JavalinTest.test(App.getApp(), (server, client) -> {
            var response = client.get("/urls/7777777");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}