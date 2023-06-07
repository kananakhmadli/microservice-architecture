package com.company.akh.gateway.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class AuthContextFilter implements WebFilter {

    private static final String X_CLIENT_ID = "X-Client-Id";
    private static final String X_CLIENT_ROLES = "X-Client-Roles";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .cast(UsernamePasswordAuthenticationToken.class)
                .doOnNext(token -> {
                    Client client = (Client) token.getPrincipal();
                    var mutate = exchange.getRequest().mutate();
                    if (Objects.nonNull(client.getClientId())) {
                        mutate.header(X_CLIENT_ID, client.getClientId());
                    }

                    if (Objects.nonNull(client.getRoles())) {
                        mutate.header(X_CLIENT_ROLES, String.join(",", client.getRoles()));
                    }
                }).then(chain.filter(exchange));
    }

}
