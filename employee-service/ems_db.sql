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

---------------------------------------------------------------------------

USE ems_db;
CREATE TABLE IF NOT EXISTS attendance_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    attendance_day DATE NOT NULL,
    event_type VARCHAR(10) NOT NULL,  -- IN or OUT
    event_time DATETIME NOT NULL,

    device_fingerprint VARCHAR(100) NOT NULL,
    qr_source VARCHAR(50) NOT NULL,

    latitude DECIMAL(10,6),
    longitude DECIMAL(10,6),

    worked_hours DOUBLE,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_emp_day(employee_id, attendance_day)
);

select * from attendance_records;

---------------------------------------------------------------------------














