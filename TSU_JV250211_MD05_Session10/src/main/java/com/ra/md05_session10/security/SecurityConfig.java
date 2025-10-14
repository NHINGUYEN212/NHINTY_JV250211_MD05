package com.ra.md05_session10.security;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.ra.md05_session10.security.jwt.JWTEntryPoint;
import com.ra.md05_session10.security.jwt.JWTFilterChain;
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

import java.util.Map;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTFilterChain jwtFilterChain;
    @Autowired
    private JWTEntryPoint jwtEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/auth/login").permitAll();
                    auth.requestMatchers("/api/v1/admin/auth/register").hasRole("ADMIN");
                    auth.requestMatchers("/api/v1/manager/**").hasAnyRole("ADMIN", "MANAGER");
                    auth.requestMatchers("/api/v1/admin/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();


                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint));
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Cloudinary cloudinary (){
        Map<String,String> config = ObjectUtils.asMap("cloud_name","dg10ca49n",
                "api_key","794153818827852","api_secret","qrAX6DlWgDl--izJcSK7gvAZ8eQ");
        return new Cloudinary(config);
    }
}