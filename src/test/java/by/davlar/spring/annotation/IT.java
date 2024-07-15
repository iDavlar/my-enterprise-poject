package by.davlar.spring.annotation;


import by.davlar.spring.ApplicationRunner;
import by.davlar.spring.config.TestApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@SpringBootTest(classes = {ApplicationRunner.class, TestApplicationConfiguration.class})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
//@SqlGroup({
//        @Sql(scripts = CLASSPATH_URL_PREFIX + "clear_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
//        @Sql(scripts = CLASSPATH_URL_PREFIX + "data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//})
//@SqlConfig(transactionMode = SqlConfig.TransactionMode.INFERRED)
public @interface IT {
}
