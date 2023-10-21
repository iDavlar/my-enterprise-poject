package by.davlar.jdbc.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pizza {
    private Integer id;
    private String name;
    private Integer cost;
}
