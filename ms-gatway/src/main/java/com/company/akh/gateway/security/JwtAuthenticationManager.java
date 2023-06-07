package com.company.akh.gateway.security;

import com.company.akh.gateway.error.InvalidTokenException;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationManager(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(UsernamePasswordAuthenticationToken.class::isInstance)
                .cast(UsernamePasswordAuthenticationToken.class)
                .flatMap(jwt -> Mono.justOrEmpty(validate(jwt)))
                .onErrorMap(error -> new InvalidTokenException(error.getMessage()));
    }

    private Authentication validate(UsernamePasswordAuthenticationToken token) {
        var jwt = (String) token.getCredentials();
        Claims claims = jwtTokenProvider.validateToken(jwt);
        return jwtTokenProvider.parseAuthentication(claims);
    }

}
