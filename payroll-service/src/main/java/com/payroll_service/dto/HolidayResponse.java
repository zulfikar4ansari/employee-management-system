package com.payroll_service.dto;

import java.time.LocalDate;

public record HolidayResponse(Long id, LocalDate holidayDate, String holidayName, Boolean paid) {}
