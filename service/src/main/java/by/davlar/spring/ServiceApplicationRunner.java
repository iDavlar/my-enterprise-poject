package by.davlar.spring;

import by.davlar.spring.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ServiceApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(ServiceApplicationRunner.class, args);
        var userService = context.getBean(UserService.class);
        userService.findAll().forEach(System.out::println);
    }
}
