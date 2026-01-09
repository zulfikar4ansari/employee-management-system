package com.common_lib.constants;

/**
 * Centralized Redis key naming conventions.
 * Ensures all services use consistent cache + session keys.
 */
public final class RedisKeys {

    // Prevent instantiation
    private RedisKeys() {}

    /** OTP:<mobile> */
    public static final String OTP = "OTP:";

    /** SESSION:<mobile> */
    public static final String SESSION = "SESSION:";

    /** EMP:<employeeId> */
    public static final String EMPLOYEE_CACHE = "EMP:";

    /** ATT:<yyyyMMdd>:<employeeId> */
    public static final String ATTENDANCE = "ATT:";
}
