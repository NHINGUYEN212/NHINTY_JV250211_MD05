package com.ra.md05_session09.security;

import com.ra.md05_session09.security.jwt.JWTEntryPoint;
import com.ra.md05_session09.security.jwt.JWTFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTFilterChain jwtFilterchain;

    private final JWTEntryPoint jwtEntryPoint = new JWTEntryPoint();


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests.requestMatchers("/auth/**").permitAll();
            authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN");
            authorizeRequests.requestMatchers("/user/**").hasRole("USER");
//            authorizeRequests.requestMatchers("/reservations/book/**").hasRole("USER");
//            authorizeRequests.requestMatchers("/reservations/cancel/**").hasAnyRole("USER", "ADMIN");
//            authorizeRequests.requestMatchers("/reservations/confirm/**").hasRole("ADMIN");
            authorizeRequests.anyRequest().authenticated();
        })
                .sessionManagement(sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilterchain, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtEntryPoint));
        return http.build();
    }
}
