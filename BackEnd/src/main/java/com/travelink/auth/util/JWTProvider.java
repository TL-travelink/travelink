package com.travelink.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JWTProvider {

    private static final long accessTokenExpireTime = 100_000_000; // 나중에 프로퍼티로 수정
    private static final long refreshTokenExpireTime = 200_000_000; // 나중에 프로퍼티로 수정

    public String createAccessToken(String userId) {
        return create(userId, "access-token", accessTokenExpireTime);
    }

    public String createRefreshToken(String userId) {
        return create(userId, "refresh-token", refreshTokenExpireTime);
    }

    private String create(String userId, String subject, long expireTime) {
        Claims claims = Jwts.claims()
            .setSubject(subject)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expireTime));

        claims.put("userId", userId);

        String jwt = Jwts.builder()
            .setHeaderParam("typ", "JWT").
            setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, this.generateKey())
            .compact();

        return jwt;
    }

    public String getPayload(final String token) {
        return Jwts.parser()
            .setSigningKey(this.generateKey())
            .parseClaimsJws(token)
            .getBody()
            .get("userId", String.class);
    }

    public boolean validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                .setSigningKey(this.generateKey())
                .parseClaimsJws(token);

            return !claims.getBody()
                .getExpiration()
                .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = "travelinkKey".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // 커스텀 에러 던지기
        }

        return key;
    }
}
