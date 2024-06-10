package by.davlar.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WebApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(WebApplicationRunner.class, args);
    }
}
