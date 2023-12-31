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
@Table(name = "address", schema = "pizzeria")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String city;
    private String region;
    private String street;
    private String apartment;
    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
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
