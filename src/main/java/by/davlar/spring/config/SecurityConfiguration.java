package by.davlar.spring.config;

import by.davlar.spring.http.utils.UrlPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                UrlPath.LOGIN, UrlPath.REGISTRATION
                        ).permitAll()
//                        .requestMatchers(
//                                UrlPath.ALL_USERS
//                        ).hasAnyAuthority(
//                                "ADMIN"
//                        )
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage(UrlPath.LOGIN)
                        .defaultSuccessUrl(UrlPath.ALL_USERS)
                        .permitAll());
        return http.build();
    }
}
