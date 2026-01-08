package com.forex.gateway.filter;

import com.forex.gateway.security.JwtUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class JwtAuthenticationFilter implements GlobalFilter {

    // paths to skip authentication (health, actuator, login, fallback)
    private static final String[] WHITELIST = new String[] {
        "/actuator", "/actuator/**", "/login", "/fallback", "/favicon.ico"
    };

    private boolean isWhiteListed(String path) {
        for (String s : WHITELIST) {
            if (path.startsWith(s.replace("/**", ""))) return true;
        }
        return false;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (isWhiteListed(path) || exchange.getRequest().getMethod().name().equals("OPTIONS")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        if (!JwtUtil.isTokenValid(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Optionally forward user info to downstream services
        String subject = JwtUtil.getSubject(token);
        exchange.getRequest().mutate()
                .header("X-Auth-User", subject)
                .build();

        return chain.filter(exchange);
    }
}