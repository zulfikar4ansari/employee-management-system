CREATE DATABASE IF NOT EXISTS ems_db;

USE ems_db;

CREATE TABLE IF NOT EXISTS employees (
    employee_id BIGINT PRIMARY KEY,
    mobile VARCHAR(20) UNIQUE NOT NULL,
    department VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    shift VARCHAR(30) NOT NULL,
    payroll_mapping_id BIGINT NOT NULL
);

INSERT INTO employees(employee_id, mobile, department, role, shift, payroll_mapping_id)
VALUES (1001, '9876543210', 'IT', 'Senior Developer', 'GENERAL', 9001)
ON DUPLICATE KEY UPDATE department=department;

select * from employees;
