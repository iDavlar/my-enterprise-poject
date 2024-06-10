package by.davlar.spring.config;

import by.davlar.spring.database.entity.*;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("by.davlar.spring")
@EnableTransactionManagement
@Import(
        {
                DatabaseApplicationConfiguration.class
        }
)
public class ServiceApplicationConfiguration {

}
