package com.ra.md05_session09.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    String secret = "osakahankyu";
    long expiration = 24 * 60 * 60 * 1000;

    public String generateToken(String email) {
        Date expirationDate = new Date(new Date().getTime() + expiration);
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody() != null;
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}

