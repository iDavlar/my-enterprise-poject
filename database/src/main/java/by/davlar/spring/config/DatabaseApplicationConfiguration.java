package by.davlar.spring.config;

import by.davlar.spring.database.entity.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("by.davlar.spring")
@EnableJpaRepositories("by.davlar.spring.database.repository")
@EntityScan(
        basePackages = "by.davlar.spring.database.entity",
        basePackageClasses = {
                Address.class,
                Order.class,
                OrderEntry.class,
                Pizza.class,
                Role.class,
                User.class
        }
)
public class DatabaseApplicationConfiguration {

}
