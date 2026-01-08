package com.forex.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
public class SecurityConfig {
	

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
            .csrf().disable()
            .authorizeExchange()
                .pathMatchers(
                    "/auth/**",
                    "/currency-exchange/**",
                    "/currency-conversion/**",
                    "/fallback",
                    "/actuator/**"
                ).permitAll()
                .anyExchange().authenticated()
            .and()
            .httpBasic().disable()
            .formLogin().disable();

        return http.build();
    }
}


