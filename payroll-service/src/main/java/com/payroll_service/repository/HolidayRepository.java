package com.payroll_service.repository;

import com.payroll_service.entity.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {
    Optional<HolidayEntity> findByHolidayDate(LocalDate date);
}
