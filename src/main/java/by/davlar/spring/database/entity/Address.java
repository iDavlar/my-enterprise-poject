package by.davlar.spring.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import static by.davlar.spring.database.utils.EntityGraphHelper.WITH_USER;

@NamedEntityGraph(
        name = WITH_USER,
        attributeNodes = {@NamedAttributeNode("user")}
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address", schema = "pizzeria")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Addresses")
public class Address implements BaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String city;
    private String region;
    private String street;
    private String apartment;

    @ManyToOne(
            cascade = {CascadeType.MERGE},
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;

    @PostLoad
    protected void repair() {
        if (city != null) {
            city = city.trim();
        }
        if (region != null) {
            region = region.trim();
        }
        if (street != null) {
            street = street.trim();
        }
        if (apartment != null) {
            apartment = apartment.trim();
        }
    }
}
