package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                    ClientRegistrationRepository clients) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/public/**", "/h2/**").permitAll()
                .anyRequest().authenticated()
        );

        http.oauth2Login(Customizer.withDefaults());

        // Cho H2 console
        http.headers(h -> h.frameOptions(f -> f.sameOrigin()));
        http.csrf(csrf -> csrf.ignoringRequestMatchers(
                new AntPathRequestMatcher("/h2/**"),
                new AntPathRequestMatcher("/api/**")
        ));

        http.logout(l -> l.logoutSuccessHandler(new OidcClientInitiatedLogoutSuccessHandler(clients)));
        return http.build();
    }
}
