package com.forex.gateway.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class LoggingGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().toString();
        String method = exchange.getRequest().getMethodValue();
        System.out.println("[Gateway][Request] " + method + " " + path + " from " + exchange.getRequest().getRemoteAddress());
        return chain.filter(exchange).doOnSuccess(done -> {
            int status = exchange.getResponse().getStatusCode() != null ? exchange.getResponse().getStatusCode().value() : 200;
            System.out.println("[Gateway][Response] " + method + " " + path + " -> " + status);
        });
    }
}