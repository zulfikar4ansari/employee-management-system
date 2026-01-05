package com.auth.service;

import com.ems.common.jwt.UserSession;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * Handles JWT access token + refresh token generation.
 */
@Service
public class JwtTokenService {

    private static final String SECRET = "ems-secret-key";

    public String generateAccessToken(String mobile, Long employeeId) {

        return Jwts.builder()
                .setSubject(mobile)                     // identifies user
                .claim("employeeId", employeeId)        // extra claim
                .setIssuedAt(new Date())                // token issue time
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public UserSession createSession(String mobile, Long employeeId) {
        return new UserSession(
                mobile,
                employeeId,
                OffsetDateTime.now()
        );
    }
}
