package by.davlar.spring.config;

import by.davlar.spring.database.entity.*;
import by.davlar.spring.mapper.RoleMapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("by.davlar.spring")
@EnableTransactionManagement
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
public class ApplicationConfiguration {
        @Bean
        @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
        RoleMapper roleMapper() {
                return RoleMapper.INSTANCE;
        }
}
