package by.davlar.hibernate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles", schema = "pizzeria")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "isadmin")
    private Boolean isAdmin;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role")
    private List<User> users;

    @PostLoad
    protected void repair() {
        if (name != null) {
            name = name.trim();
        }
    }
}
