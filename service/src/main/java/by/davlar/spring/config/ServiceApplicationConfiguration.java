package by.davlar.spring.config;

import by.davlar.spring.mapper.RoleMapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("by.davlar.spring")
@EnableTransactionManagement
@Import(
        {
                DatabaseApplicationConfiguration.class
        }
)
public class ServiceApplicationConfiguration {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    RoleMapper roleMapper() {
        return RoleMapper.INSTANCE;
    }
}
