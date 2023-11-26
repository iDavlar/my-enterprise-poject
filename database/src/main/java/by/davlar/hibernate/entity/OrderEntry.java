package by.davlar.hibernate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_entries", schema = "pizzeria")
public class OrderEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer amount;
    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;
}
