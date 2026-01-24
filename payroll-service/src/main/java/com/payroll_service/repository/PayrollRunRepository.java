package com.payroll_service.repository;

import com.payroll_service.entity.PayrollRunEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayrollRunRepository extends JpaRepository<PayrollRunEntity, Long> {
    Optional<PayrollRunEntity> findByYearAndMonth(Integer year, Integer month);
}
