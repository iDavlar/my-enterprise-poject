package by.davlar.hibernate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@FetchProfile(
        name = "withRole",
        fetchOverrides = {
                @FetchProfile.FetchOverride(entity = User.class, association = "role", mode = FetchMode.JOIN)
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "pizzeria")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private Date birthday;
    private String login;
    private String password;
    private String telephone;

    @ManyToOne(
            cascade = {CascadeType.MERGE},
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "role")
    private Role role;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(
            cascade = {CascadeType.MERGE},
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Order> orders = new ArrayList<>();

    @PostLoad
    protected void repair() {
        if (firstName != null) {
            firstName = firstName.trim();
        }
        if (lastName != null) {
            lastName = lastName.trim();
        }
        if (login != null) {
            login = login.trim();
        }
        if (password != null) {
            password = password.trim();
        }
        if (telephone != null) {
            telephone = telephone.trim();
        }
    }
}
