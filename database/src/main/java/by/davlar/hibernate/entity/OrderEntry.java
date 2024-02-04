package by.davlar.hibernate.entity;

import by.davlar.hibernate.utils.FetchProfileHelper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import static by.davlar.hibernate.utils.FetchProfileHelper.WITH_ORDER_AND_PIZZA;

@FetchProfile(
        name = WITH_ORDER_AND_PIZZA,
        fetchOverrides = {
                @FetchProfile.FetchOverride(entity = OrderEntry.class, association = "order", mode = FetchMode.JOIN),
                @FetchProfile.FetchOverride(entity = OrderEntry.class, association = "pizza", mode = FetchMode.JOIN)
        }
)
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

    @ManyToOne(
            cascade = {CascadeType.MERGE},
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(
            cascade = {CascadeType.MERGE},
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;
}
