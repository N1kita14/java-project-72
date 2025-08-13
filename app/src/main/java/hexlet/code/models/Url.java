package hexlet.code.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter

public class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;

    public Url(Long id, String name, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }
    public Url(String name){
        this.name = name;
    }
}
