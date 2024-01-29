package by.davlar.hibernate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders", schema = "pizzeria")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data")
    private LocalDateTime date;

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "address_id")
    private Address address;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "order")
    private List<OrderEntry> entries = new ArrayList<>();
}
