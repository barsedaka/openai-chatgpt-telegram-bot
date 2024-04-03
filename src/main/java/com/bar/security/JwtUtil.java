package com.bar.security;

import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.util.function.Function;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public boolean isValidToken(final String bearerToken) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(bearerToken)
                    .getBody();
            return !body.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Expired or invalid JWT token");
        }
    }

    public String getUserName(final String bearerToken) {
        return getClaimsFromToken(bearerToken, Claims::getSubject);
    }

    private <T> T getClaimsFromToken(
            final String bearerToken, Function<Claims, T> disassembleClaims
    ) {
        return disassembleClaims.apply(
                Jwts.parserBuilder()
                        .setSigningKey(secret)
                        .build()
                        .parseClaimsJws(bearerToken)
                        .getBody()
        );
    }
}
