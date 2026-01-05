package com.auth.service;

import com.ems.common.constants.RedisKeys;
import com.ems.common.jwt.UserSession;
import com.ems.common.util.JsonUtils;
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
