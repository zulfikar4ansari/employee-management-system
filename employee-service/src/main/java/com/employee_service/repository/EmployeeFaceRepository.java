package com.employee_service.repository;

import com.employee_service.entity.EmployeeFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeFaceRepository extends JpaRepository<EmployeeFaceEntity, Long> {
    Optional<EmployeeFaceEntity> findByEmployeeId(Long employeeId);
    void deleteByEmployeeId(Long employeeId);
}
