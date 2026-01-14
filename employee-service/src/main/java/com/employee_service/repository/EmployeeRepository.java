package com.employee_service.repository;

import com.employee_service.entity.EmployeeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository provides CRUD operations and query methods.
 */
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByMobile(String mobile);
}
