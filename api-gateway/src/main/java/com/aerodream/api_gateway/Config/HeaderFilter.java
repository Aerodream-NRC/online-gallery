package com.aerodream.api_gateway.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class HeaderFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getPrincipal()
                .filter(principal -> principal instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .map(JwtAuthenticationToken::getToken)
                .flatMap(jwt -> {
                    String userId = jwt.getClaimAsString("UserId");

                    ServerHttpRequest mutateRequest = exchange.getRequest().mutate()
                            .header("X-User-Id", userId)
                            .build();

                    log.info("Header created with ID: {}", userId);

                    return chain.filter(exchange.mutate().request(mutateRequest).build());
                })
                .switchIfEmpty(chain.filter(exchange));
    }
}
