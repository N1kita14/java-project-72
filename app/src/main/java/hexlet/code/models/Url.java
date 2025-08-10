package hexlet.code.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class Url {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
