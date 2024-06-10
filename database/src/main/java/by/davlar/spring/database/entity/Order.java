package by.davlar.spring.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static by.davlar.spring.database.utils.EntityGraphHelper.WITH_USER_AND_ADDRESS;


@NamedEntityGraph(
        name = WITH_USER_AND_ADDRESS,
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("address")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders", schema = "pizzeria")
public class Order implements BaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data")
    private LocalDateTime date;

    @ManyToOne(cascade = {
            CascadeType.MERGE},
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {
            CascadeType.MERGE},
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "address_id")
    private Address address;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(
            cascade = {CascadeType.MERGE},
            mappedBy = "order",
            fetch = FetchType.LAZY
    )
    private List<OrderEntry> entries = new ArrayList<>();
}
