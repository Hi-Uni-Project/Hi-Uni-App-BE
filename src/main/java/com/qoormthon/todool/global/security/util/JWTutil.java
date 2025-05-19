package com.qoormthon.todool.global.security.util;

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
    private final int accessExpiredms;
    private final int refreshExpiredms;

    @Autowired
    public JWTutil(SecretKey secretKey, @Value("${jwt.access.expiration}")int accessExpiredms
    , @Value("${jwt.refresh.expiration}")int refreshExpiredms) {
        this.accessExpiredms = accessExpiredms;
        this.refreshExpiredms = refreshExpiredms;
        this.secretKey = secretKey;
    }
    public String getUserId(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("sub", String.class);
    }

    public String getRole(String token){
        String role = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("roles", String.class);
        return role;
    }

    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public Date getAccessExpirationDate() {
        return new Date(getCurrentDate().getTime() + accessExpiredms);
    }

    public Date getRefreshExpirationDate() {
        return new Date(getCurrentDate().getTime() + refreshExpiredms);
    }

    public boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String getType(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("type", String.class);
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


    public String createAccessToken(String stdNo, String role){
        return Jwts.builder()
                .claim("type", "access")
                .setSubject(stdNo)
                .issuedAt(getCurrentDate())
                .expiration(getAccessExpirationDate())
                .claim("roles", "ROLE_"+role)
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String stdNo, String role){
        return Jwts.builder()
                .claim("type", "refresh")
                .setSubject(stdNo)
                .issuedAt(getCurrentDate())
                .expiration(getRefreshExpirationDate())
                .claim("roles", "ROLE_"+role)
                .signWith(secretKey)
                .compact();
    }

}
