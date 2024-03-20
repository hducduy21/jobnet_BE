package com.jobnet.apigateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.signingKey}")
    private String jwtSigningKey;

    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(jwtSigningKey)
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Token is expired.");
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid token.");
        }
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        final Date expiration = this.extractExpiration(token);
        return username.equals(userDetails.getUsername())
            && expiration.after(new Date());
    }

    public String generateToken(UserDetails userDetails, long millis) {
        Map<String, Object> claims = new HashMap<>();
        return this.createToken(claims, userDetails, millis);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails, long millis) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .claim("authorities", userDetails.getAuthorities())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + millis))
            .signWith(SignatureAlgorithm.HS256, jwtSigningKey)
            .compact();
    }
}
