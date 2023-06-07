package com.company.akh.gateway.config;

import az.kapitalbank.atlas.lib.common.error.RestErrorResponse;
import com.company.akh.gateway.mapper.JsonMapper;
import com.company.akh.gateway.security.AuthContextFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;
import java.util.List;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    private static final String[] IGNORE_PATHS = new String[]{
            "/actuator/health",
            "/actuator/refresh",
            "/api/auth/login"
    };

    private final JsonMapper jsonMapper;

    public SecurityConfiguration(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            ReactiveAuthenticationManager authManager,
            ServerAuthenticationConverter authConverter) {

        var filter = new AuthenticationWebFilter(authManager);
        filter.setServerAuthenticationConverter(authConverter);
        filter.setAuthenticationFailureHandler(
                new ServerAuthenticationEntryPointFailureHandler(authenticationEntryPoint()));

        http
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers(IGNORE_PATHS).permitAll()
                .anyExchange().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAfter(new AuthContextFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable();

        return http.build();
    }

    @Bean
    ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, ex) ->
                produceErrorResponse(exchange, ex, HttpStatus.UNAUTHORIZED);
    }

    @Bean
    ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, ex) ->
                produceErrorResponse(exchange, ex, HttpStatus.FORBIDDEN);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Collections.singletonList("https://*.kapitalbank.az"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedOriginPatterns(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private Mono<Void> produceErrorResponse(ServerWebExchange exchange,
                                            RuntimeException ex,
                                            HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().setCacheControl("no-cache");

        var errorUuid = UUID.randomUUID().toString();
        log.error("Authentication failed error, uuid: {}, code: {}, message: {}",
                errorUuid, httpStatus.name(), ex.getMessage());


        var errorResponse = new RestErrorResponse(
                errorUuid,
                httpStatus.name(),
                httpStatus.getReasonPhrase());

        DataBuffer buffer = response.bufferFactory()
                .wrap(jsonMapper.toJSONString(errorResponse).getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }

}
