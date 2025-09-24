package com.market_be.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    static final String PREFIX = "Bearer ";
    static final Long EXPIRATION_TIME = 24*60*60*1000L;
    static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // username(ID)를 받아서 JWT 생성
    public String generateToken(String loginId) {
        return Jwts.builder()
                .setSubject(loginId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY)
                .compact();
    }

    // JWT를 받아서 username(ID)를 반환
    public String parseToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build();
            return parser.parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException e) {
            System.err.println("JWT 파싱 실패: " + e.getMessage());
            return null;
        }
    }
}
