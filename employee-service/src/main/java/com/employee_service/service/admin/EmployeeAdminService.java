package com.employee_service.service.admin;

import com.employee_service.dto.EmployeeProfileResponse;
import com.employee_service.dto.admin.EmployeeCreateRequest;
import com.employee_service.dto.admin.EmployeeUpdateRequest;
import com.employee_service.entity.EmployeeEntity;
import com.employee_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeAdminService {

    private final EmployeeRepository repo;

    public EmployeeAdminService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public EmployeeProfileResponse create(EmployeeCreateRequest req) {

        if (repo.existsById(req.employeeId())) {
            throw new RuntimeException("Employee already exists with id: " + req.employeeId());
        }

        EmployeeEntity e = new EmployeeEntity();
        e.setEmployeeId(req.employeeId());
        e.setMobile(req.mobile());
        e.setDepartment(req.department());
        e.setRole(req.role());
        e.setShift(req.shift());
        e.setPayrollMappingId(req.payrollMappingId());

        repo.save(e);

        return toProfile(e);
    }

    public EmployeeProfileResponse update(Long employeeId, EmployeeUpdateRequest req) {

        EmployeeEntity e = repo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

        e.setDepartment(req.department());
        e.setRole(req.role());
        e.setShift(req.shift());
        e.setPayrollMappingId(req.payrollMappingId());

        repo.save(e);

        return toProfile(e);
    }

    public EmployeeProfileResponse get(Long employeeId) {
        EmployeeEntity e = repo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));
        return toProfile(e);
    }

    public List<EmployeeProfileResponse> list() {
        return repo.findAll().stream().map(this::toProfile).toList();
    }

    public void delete(Long employeeId) {
        repo.deleteById(employeeId);
    }

    private EmployeeProfileResponse toProfile(EmployeeEntity e) {
        return new EmployeeProfileResponse(
                e.getEmployeeId(),
                e.getDepartment(),
                e.getRole(),
                e.getShift(),
                e.getPayrollMappingId()
        );
    }
}
