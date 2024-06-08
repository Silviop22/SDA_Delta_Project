package com.ecommerce.ecommerce.config;

import com.ecommerce.ecommerce.security.JWTFilter;
import com.ecommerce.ecommerce.services.UserDetailsServiceImpl;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Updated way to disable CSRF
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(AppConstants.PUBLIC_URLS).permitAll()  // Public URLs accessible by anyone
                        .requestMatchers(AppConstants.USER_URLS).hasAnyAuthority("USER", "ADMIN")  // URLs for USER and ADMIN roles
                        .requestMatchers(AppConstants.ADMIN_URLS).hasAuthority("ADMIN")  // URLs restricted to ADMIN role
                        .anyRequest().authenticated()  // All other requests require authentication
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))  // Custom unauthorized response
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session management for REST APIs
                .authenticationProvider(daoAuthenticationProvider())  // Set the DAO Authentication Provider
                .addFilterBefore((Filter) jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before the UsernamePasswordAuthenticationFilter

        // Build and return the default security filter chain
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
