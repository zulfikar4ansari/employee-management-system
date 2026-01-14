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
VALUES (1003, '9876543213', 'IT', 'Senior Developer', 'GENERAL', 9003)
ON DUPLICATE KEY UPDATE department=department;

select * from employees;

---------------------------------------------------------------------------
USE ems_db;

CREATE TABLE IF NOT EXISTS qt_table (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    qr_date DATE NOT NULL,
    qr_code_value CHAR(16) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_qr_date_code(qr_date, qr_code_value)
);

INSERT INTO qt_table (qr_date, qr_code_value, is_active) VALUES
('2026-01-01','2026010100000001', TRUE),('2026-01-02','2026010200000001', TRUE),('2026-01-03','2026010300000001', TRUE),('2026-01-04','2026010400000001', TRUE),
('2026-01-05','2026010500000001', TRUE),('2026-01-06','2026010600000001', TRUE),('2026-01-07','2026010700000001', TRUE),('2026-01-08','2026010800000001', TRUE),
('2026-01-09','2026010900000001', TRUE),('2026-01-10','2026011000000001', TRUE),('2026-01-11','2026011100000001', TRUE),('2026-01-12','2026011200000001', TRUE),
('2026-01-13','2026011300000001', TRUE),('2026-01-14','2026011400000001', TRUE),('2026-01-15','2026011500000001', TRUE),('2026-01-16','2026011600000001', TRUE),
('2026-01-17','2026011700000001', TRUE),('2026-01-18','2026011800000001', TRUE),('2026-01-19','2026011900000001', TRUE),('2026-01-20','2026012000000001', TRUE),
('2026-01-21','2026012100000001', TRUE),('2026-01-22','2026012200000001', TRUE),('2026-01-23','2026012300000001', TRUE),('2026-01-24','2026012400000001', TRUE),
('2026-01-25','2026012500000001', TRUE),('2026-01-26','2026012600000001', TRUE),('2026-01-27','2026012700000001', TRUE),('2026-01-28','2026012800000001', TRUE),
('2026-01-29','2026012900000001', TRUE),('2026-01-30','2026013000000001', TRUE);

select * from qt_table; 

-------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS attendance_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    employee_mobile VARCHAR(20),
    attendance_date DATE NOT NULL,
    attendance_time TIME NOT NULL,
    qr_date DATE NOT NULL,
    qr_code_value CHAR(16) NOT NULL,
    status VARCHAR(20) NOT NULL,
    reason VARCHAR(200),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_emp_date(employee_id, attendance_date)
);

SHOW INDEX FROM attendance_records;

ALTER TABLE attendance_records ADD COLUMN time_in TIME NULL;
ALTER TABLE attendance_records ADD COLUMN time_out TIME NULL;
ALTER TABLE attendance_records ADD COLUMN worked_minutes INT NULL;
SHOW COLUMNS FROM attendance_records;
ALTER TABLE attendance_records MODIFY qr_code_value VARCHAR(50) NULL;
ALTER TABLE attendance_records MODIFY qr_date DATE NULL;

-------------------------------------------------------------------------------

USE ems_db;
select * from employees;
select * from qt_table; 
select * from attendance_records;

delete from attendance_records where employee_mobile ='9876543210';

SELECT * 
FROM attendance_records 
WHERE employee_id = 1003 AND attendance_date = CURDATE();

DELETE FROM attendance_records 
WHERE employee_id = 1001 AND attendance_date = CURDATE();

-----


















