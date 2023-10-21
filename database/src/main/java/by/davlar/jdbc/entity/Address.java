package by.davlar.jdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Stack;

@Data
@Builder
public class Address {
    private Integer id;
    private Integer userId;
    private String city;
    private String region;
    private String street;
    private String apartment;
    private User user;
}
