package com.api_gateway.rate;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Rate limiting key strategy.
 * If employeeId exists, rate-limit per employee.
 * Else fallback to IP address.
 */
@Component("rateLimiterKeyResolver")
public class RateLimiterKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {

        String empId = exchange.getRequest().getHeaders().getFirst("X-Employee-Id");

        if (empId != null && !empId.isBlank()) {
            return Mono.just("EMPLOYEE:" + empId);
        }

        String ip = "UNKNOWN";
        if (exchange.getRequest().getRemoteAddress() != null) {
            ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        }

        return Mono.just("IP:" + ip);
    }
}
