package by.davlar.hibernate.entity;

import by.davlar.hibernate.utils.FetchProfileHelper;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static by.davlar.hibernate.utils.FetchProfileHelper.WITH_USER_AND_ADDRESS;

@FetchProfile(
        name = WITH_USER_AND_ADDRESS,
        fetchOverrides = {
                @FetchProfile.FetchOverride(entity = Order.class, association = "user", mode = FetchMode.JOIN),
                @FetchProfile.FetchOverride(entity = Order.class, association = "address", mode = FetchMode.JOIN)
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
