package by.davlar.jdbc.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderEntry {
    private Integer id;
    private Integer orderId;
    private Integer pizzaId;
    private Integer amount;
    private Order order;
    private Pizza pizza;
}
