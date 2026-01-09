package com.auth.service;

import com.ems.common.jwt.UserSession;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;


import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * Handles JWT access token and refresh token generation.
 * Uses proper HMAC key handling as required by JJWT 0.11+.
 */
@Service
public class JwtTokenService {

    /**
     * HS256 requires a minimum 256-bit (32-byte) secret.
     * NEVER use short strings in production.
     */
    private static final String SECRET =
            "ems-secret-key-ems-secret-key-ems-secret-key";

    /**
     * Convert the secret into a cryptographically valid HMAC key.
     */
    private final SecretKey signingKey =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * Generates JWT access token.
     */
    public String generateAccessToken(String mobile, Long employeeId) {

        return Jwts.builder()
                .setSubject(mobile)                 // Principal
                .claim("employeeId", employeeId)    // Custom claim
                .setIssuedAt(new Date())             // Issued time
                .setExpiration(
                        new Date(System.currentTimeMillis() + 3600_000)
                )                                    // 1 hour expiry
                .signWith(signingKey)                // ✅ CORRECT
                .compact();
    }

    /**
     * Generates refresh token (UUID-based).
     */
    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Creates a session object to store in Redis.
     */
    public UserSession createSession(String mobile, Long employeeId) {
        return new UserSession(
                mobile,
                employeeId,
                OffsetDateTime.now()
        );
    }
}