package by.davlar.spring.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles", schema = "pizzeria")
@Audited
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Roles")
public class Role implements BaseEntity<Integer>, GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "isadmin")
    private Boolean isAdmin;

    @ToString.Exclude
    @OneToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "role")
    private List<User> users;

    @PostLoad
    protected void repair() {
        if (name != null) {
            name = name.trim();
        }
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
