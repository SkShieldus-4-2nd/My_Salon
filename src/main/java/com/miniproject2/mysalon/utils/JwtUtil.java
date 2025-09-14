package com.miniproject2.mysalon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // For simplicity, hardcoding the secret key. In a real application, this should be in application.properties.
    @Value("${jwt.secret:MySalonSecretKeyForJWTGeneration}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 10; // 10 hours

    public String generateToken(String userId, Long userNum) {
        return JWT.create()
                .withSubject(userId)
                .withClaim("userNum", userNum)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token);
    }

    public Long getUserNum(String token) {
        return validateToken(token).getClaim("userNum").asLong();
    }

    public String getUserId(String token) {
        return validateToken(token).getSubject();
    }
}