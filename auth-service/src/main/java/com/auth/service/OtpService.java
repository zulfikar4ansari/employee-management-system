package com.auth.service;

import com.ems.common.constants.RedisKeys;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Generates OTP and stores it temporarily in Redis.
 */
@Service
public class OtpService {

    private final StringRedisTemplate redis;

    public OtpService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public String generateOtp(String mobile) {

        // Generate 6-digit OTP
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        // Store in Redis — expires in 5 mins
        redis.opsForValue().set(
                RedisKeys.OTP + mobile,
                otp,
                5,
                TimeUnit.MINUTES
        );

        // In real system → send via WhatsApp API
        System.out.println("OTP sent to WhatsApp = " + otp);

        return otp;
    }

    public boolean validateOtp(String mobile, String input) {

        String stored = redis.opsForValue().get(RedisKeys.OTP + mobile);

        return stored != null && stored.equals(input);
    }
}
