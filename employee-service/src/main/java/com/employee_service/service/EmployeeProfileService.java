package com.employee_service.service;


import com.common_lib.constants.RedisKeys;
import com.common_lib.utils.JsonUtils;
import com.employee_service.dto.EmployeeProfileResponse;
import com.employee_service.entity.EmployeeEntity;
import com.employee_service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * Cache-first employee profile service.
 *
 * Flow:
 * 1) Try Redis cache first
 * 2) If miss -> fetch from MySQL
 * 3) Store in Redis for next call
 */
@Service
public class EmployeeProfileService {

    private final StringRedisTemplate redis;
    private final EmployeeRepository repository;

    @Value("${employee.cache.ttl-seconds:600}")
    private long ttlSeconds;

    public EmployeeProfileService(StringRedisTemplate redis, EmployeeRepository repository) {
        this.redis = redis;
        this.repository = repository;
    }

    public EmployeeProfileResponse getEmployeeProfileById(Long employeeId) {

        // Redis key uses common-lib constant for consistency
        String redisKey = RedisKeys.EMPLOYEE_CACHE + employeeId;

        // 1) Cache Lookup
        String cachedJson = redis.opsForValue().get(redisKey);
        if (cachedJson != null) {
            return JsonUtils.fromJson(cachedJson, EmployeeProfileResponse.class);
        }

        // 2) DB fallback
        EmployeeEntity entity = repository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

        EmployeeProfileResponse response = new EmployeeProfileResponse(
                entity.getEmployeeId(),
                entity.getDepartment(),
                entity.getRole(),
                entity.getShift(),
                entity.getPayrollMappingId()
        );

        // 3) Store in Redis
        redis.opsForValue().set(
                redisKey,
                JsonUtils.toJson(response),
                ttlSeconds,
                TimeUnit.SECONDS
        );

        return response;
    }

    public EmployeeEntity findByMobile(String mobile) {
        return repository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Employee not found for mobile: " + mobile));
    }


}
