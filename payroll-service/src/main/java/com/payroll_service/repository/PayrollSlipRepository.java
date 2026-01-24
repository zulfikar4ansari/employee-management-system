package com.payroll_service.repository;

import com.payroll_service.entity.PayrollSlipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PayrollSlipRepository extends JpaRepository<PayrollSlipEntity, Long> {
    List<PayrollSlipEntity> findByPayrollRunId(Long runId);
    Optional<PayrollSlipEntity> findByPayrollRunIdAndEmployeeId(Long runId, Long empId);
}
