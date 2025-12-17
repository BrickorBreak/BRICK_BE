package com.brick.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component   // 이거 반드시 필요
public class JwtTokenProvider {

    private static final String SECRET_KEY =
            "brick-secret-key-brick-secret-key-brick"; // 32byte 이상

    private static final long TOKEN_VALID_TIME =
            1000L * 60 * 60 * 24; // 24시간

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // 토큰 생성
    public String createToken(Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + TOKEN_VALID_TIME);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // userId 추출
    public Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
