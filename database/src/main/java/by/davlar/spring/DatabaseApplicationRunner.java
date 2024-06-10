package by.davlar.spring;

import by.davlar.spring.database.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DatabaseApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(DatabaseApplicationRunner.class, args);
        var userRepository = context.getBean(UserRepository.class);
        System.out.println( userRepository.findById(2).orElseThrow());
    }
}
