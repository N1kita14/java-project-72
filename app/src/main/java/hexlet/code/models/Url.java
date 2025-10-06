package hexlet.code.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    private Long id;

    private String name;
    private Timestamp createdAt;
    private UrlCheck lastCheck;
    private Integer statusCode;

    public Url(String name) {
        this.name = name;
    }
}
