package com.ismhac.jspace.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Enable CORS and disable CSRF
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                /* Set permissions on endpoints */
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/actuator/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/api-docs/**",
                                "/swagger-ui.html",
                                "/favicon.ico")
                        .permitAll()
                        .requestMatchers("/error")
                        .permitAll()
                        .requestMatchers(
                                "/api/v1/employees/**",
                                "/api/v1/auth/login",
                                "/api/v1/auth/admin/login",
                                "/api/v1/payapp/payment-charge-money/success"
                        )
                        .permitAll()
                        .anyRequest().authenticated())
                // Set session management to stateless, session won't be used to store user's
                // state.
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                // Set unauthorized requests exception handler
//                .exceptionHandling(exceptionHandling -> exceptionHandlin
//                        .authenticationEntryPoint(accessTokenEntryPoint))
//                // Add JWT token filter to validate the tokens with every request
//                .addFilterBefore(jwtTokenFilter(), CustomAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
