package by.davlar.hibernate.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
@Log4j
public class ConfigurationManager {

    private static volatile SessionFactory sessionFactory;
    private static volatile Configuration configuration;

    public Configuration getConfiguration() {
        log.trace("Configuration request");
        if (configuration == null) {
            synchronized (ConfigurationManager.class) {
                if (configuration == null) {
                    configuration = new Configuration();
                    configuration.configure();
                    configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
                }
            }
        }

        return configuration;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (ConfigurationManager.class) {
                if (sessionFactory == null) {
                    Configuration configuration = getConfiguration();
                    sessionFactory = configuration.buildSessionFactory();
                }
            }
        }

        return sessionFactory;
    }
}
