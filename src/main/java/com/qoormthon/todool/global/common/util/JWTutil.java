package com.qoormthon.todool.global.common.util;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTutil {
    private final SecretKey secretKey;
    private final int expiredms;

    @Autowired
    public JWTutil(SecretKey secretKey, @Value("${jwt.expiration}")int expiredms){
        this.expiredms = expiredms;
        this.secretKey = secretKey;
    }
    public String getUserId(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("sub", String.class);
    }

    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("roles", String.class);
    }

    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public Date getExpirationDate() {
        return new Date(getCurrentDate().getTime() + expiredms);
    }

    public Boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        } else {
            token = token.substring(7);
            return token;
        }
    }


    public String createToken(String stdNo, String role){
        return Jwts.builder()
                .setSubject(stdNo)
                .issuedAt(getCurrentDate())
                .expiration(getExpirationDate())
                .claim("roles", "ROLE_"+role)
                .signWith(secretKey)
                .compact();
    }

}
