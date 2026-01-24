package com.payroll_service.dto;

import java.time.LocalDate;

public record HolidayRequest(LocalDate holidayDate, String holidayName, Boolean paid) {}
