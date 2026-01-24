package com.payroll_service.repository;

import com.payroll_service.entity.PayrollConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollConfigRepository extends JpaRepository<PayrollConfigEntity, Long> {}
