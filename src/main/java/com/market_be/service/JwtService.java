package com.market_be.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    static final String PREFIX = "Bearer ";
    static final Long EXPIRATION_TIME = 24 * 60 * 60 * 1000L;

    // ✅ 고정된 시크릿 키 사용
    private static final String SECRET = "your-very-secure-secret-key-should-be-long";
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String loginId) {
        return Jwts.builder()
                .setSubject(loginId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY)
                .compact();
    }

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

    public String parseToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring("Bearer ".length());
            return parseToken(token); // 핵심 로직 재사용
        }
        return null;
    }
}