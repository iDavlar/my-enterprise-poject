package by.davlar.jdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Order {
    private Integer id;
    private Integer userId;
    private Integer addressId;
    private Timestamp date;
    private User user;
    private Address address;
}
