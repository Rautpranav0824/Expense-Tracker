package com.PranavRaut.Expense_Tracker.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTutil {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    public  String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(String username) {
        Map<String,Object> claim = new HashMap<>();
        return createToken(claim , username);
    }

    private String createToken(Map<String, Object> claim, String username) {
        return Jwts.builder()
                .claims(claim)
                .subject(username)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return  extractAllClaims(token).getExpiration();
    }
}
