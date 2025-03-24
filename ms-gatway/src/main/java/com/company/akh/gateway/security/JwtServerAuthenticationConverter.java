package com.company.akh.gateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return Mono.empty();
        }

        var authorization = exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

        Mono<Authentication> mono = Mono.justOrEmpty(authorization)
                .filter(v -> v.startsWith("Bearer "))
                .map(v -> v.substring(7))
                .map(jwt -> new UsernamePasswordAuthenticationToken(jwt, jwt));

        return mono.switchIfEmpty(Mono.empty());
    }

}
