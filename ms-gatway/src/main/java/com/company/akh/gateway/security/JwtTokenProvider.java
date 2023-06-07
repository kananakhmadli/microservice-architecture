package com.company.akh.gateway.security;

import com.company.akh.gateway.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key signingKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.signingKey = getPublicKey(jwtProperties.getBase64Secret());
    }

    public static PublicKey getPublicKey(String publicKey) {
        try {
            byte[] encodedPublicKey = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedPublicKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception ex) {
            log.error("Failed to build PublicKey, " + ex.getMessage());
        }
        return null;
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication parseAuthentication(Claims claims) {
        var user = Client.builder()
                .clientId(claims.get(JwtTokenField.CLIENT_ID, String.class))
                .roles(getRoles(claims))
                .build();

        return new UsernamePasswordAuthenticationToken(
                user,
                "",
                mapGrantedAuthorities(user.getRoles()));
    }

    private List<String> getRoles(Claims claims) {
        return extractRealmRoles(claims);
    }

    @SuppressWarnings("unchecked")
    private List<String> extractRealmRoles(Claims claims) {
        Map<String, List<String>> realmAccess =
                claims.get(JwtTokenField.REALM_ACCESS, Map.class);
        if (realmAccess == null) {
            return List.of();
        }
        return realmAccess.get(JwtTokenField.ROLES);
    }

    private List<GrantedAuthority> mapGrantedAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

}
