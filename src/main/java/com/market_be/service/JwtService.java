package com.market_be.service;

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
import java.util.List;

@Service
public class JwtService {

    static final String PREFIX = "Bearer ";
    static final Long EXPIRATION_TIME = 24 * 60 * 60 * 1000L;

    // ✅ 고정된 시크릿 키 사용
    private static final String SECRET = "your-very-secure-secret-key-should-be-long";
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String loginId,  List<String> roles) {
        return Jwts.builder()
                .setSubject(loginId)
                .claim("roles", roles)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY)
                .compact();
    }

    public String parseToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(PREFIX)) {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build();
            return parser.parseClaimsJws(header.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();
        }
        return null;
    }

    public String parseTokenFromTokenOnly(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();   // loginId 반환
        } catch (Exception e) {
            return null;
        }
    }

}