package by.davlar.jdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private Integer id;
    private Integer userId;
    private Integer addressId;
    private LocalDateTime date;
    private User user;
    private Address address;
}
