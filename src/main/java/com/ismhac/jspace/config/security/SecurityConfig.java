package com.ismhac.jspace.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                // Enable CORS and disable csrf
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable);
//    }

}
