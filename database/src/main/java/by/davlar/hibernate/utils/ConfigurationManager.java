package by.davlar.hibernate.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
@Log4j
public class ConfigurationManager {


    public Configuration getConfiguration() {
        log.trace("Configuration request");
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        return configuration;
    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = getConfiguration();
        return configuration.buildSessionFactory();
    }
}
