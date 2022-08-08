package com.lex.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@EnableWebSecurity
@Slf4j
public class ApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/users").hasRole("ADMIN");
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2y$10$cWbRhu7hLEwE6azH1YBRzumFHoPGSTyXSRoNQYfWuC9Wc/Nz4AyZG")
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("rafa")
                .password("{bcrypt}$2y$10$cWbRhu7hLEwE6azH1YBRzumFHoPGSTyXSRoNQYfWuC9Wc/Nz4AyZG")
                .roles("USER")
                .build();

        UserDetails system = User.builder()
                .username("system")
                .password("{bcrypt}$2y$10$cWbRhu7hLEwE6azH1YBRzumFHoPGSTyXSRoNQYfWuC9Wc/Nz4AyZG")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin, user, system);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
