package com.auth.service;

import com.common_lib.constants.RedisKeys;
import com.common_lib.jwt.UserSession;
import com.common_lib.utils.JsonUtils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Stores logged-in user session in Redis.
 */
@Service
public class AuthSessionService {

    private final StringRedisTemplate redis;

    public AuthSessionService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public void storeSession(UserSession session) {

        String key = RedisKeys.SESSION + session.mobile();

        redis.opsForValue().set(
                key,
                JsonUtils.toJson(session)
        );
    }
}
