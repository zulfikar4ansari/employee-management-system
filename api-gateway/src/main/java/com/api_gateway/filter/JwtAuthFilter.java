package com.api_gateway.filter;

import com.api_gateway.config.SecurityRouteValidator;
import com.api_gateway.jwt.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Global filter that secures protected routes with JWT validation.
 * Adds identity headers for downstream services.
 */
@Component
public class JwtAuthFilter implements GlobalFilter {

    private final SecurityRouteValidator routeValidator;
    private final JwtTokenValidator validator;



    public JwtAuthFilter(SecurityRouteValidator routeValidator, JwtTokenValidator validator) {
        this.routeValidator = routeValidator;
        this.validator = validator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().value();



        // Public endpoints do not require JWT
        if (routeValidator.isPublic(path)) {
            return chain.filter(exchange);
        }

        // Protected endpoints require Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = validator.validateAndGetClaims(token);

            String mobile = claims.getSubject();
            Object employeeId = claims.get("employeeId");

            String role = (String) claims.get("role");

            // Forward claims as headers (services should trust gateway)
            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-Mobile", mobile)
                    .header("X-Employee-Id", String.valueOf(employeeId))
                    .header("X-Role", role == null ? "" : role)   // ✅ NEW
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception ex) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
