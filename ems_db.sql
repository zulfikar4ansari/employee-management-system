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

INSERT INTO employees(employee_id, mobile, department, role, shift, payroll_mapping_id)
VALUES (1, '9000000001', 'IT', 'ADMIN', 'General', 1001);

INSERT INTO employees(employee_id, mobile, department, role, shift, payroll_mapping_id)
VALUES (103, '9000000002', 'IT', 'EMPLOYEE', 'General', 1003);

select * from employees;

-- delete from employees where employee_id in ('1001','1002','1003');

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

-----
-- Payroll System

CREATE TABLE payroll_config (
  employee_id BIGINT NOT NULL,
  gross_salary DECIMAL(10,2) NOT NULL,
  basic DECIMAL(10,2) NOT NULL,
  hra DECIMAL(10,2) NOT NULL,
  special_allowance DECIMAL(10,2) NOT NULL,

  pf_enabled TINYINT(1) NOT NULL DEFAULT 1,
  pf_percent DECIMAL(5,2) NOT NULL DEFAULT 12.00,
  esi_enabled TINYINT(1) NOT NULL DEFAULT 0,
  esi_percent DECIMAL(5,2) NOT NULL DEFAULT 0.75,
  pt_amount DECIMAL(10,2) NOT NULL DEFAULT 200.00,

  salary_days_mode VARCHAR(30) NOT NULL DEFAULT 'CALENDAR_DAYS',

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL,

  PRIMARY KEY(employee_id),
  CONSTRAINT fk_payroll_emp FOREIGN KEY (employee_id)
      REFERENCES employees(employee_id) ON DELETE CASCADE
);


CREATE TABLE holiday_calendar (
  id BIGINT NOT NULL AUTO_INCREMENT,
  holiday_date DATE NOT NULL,
  holiday_name VARCHAR(100) NOT NULL,
  is_paid TINYINT(1) NOT NULL DEFAULT 1,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  UNIQUE KEY uq_holiday_date (holiday_date)
);

CREATE TABLE weekly_off_config (
  id BIGINT NOT NULL AUTO_INCREMENT,
  sunday_off TINYINT(1) NOT NULL DEFAULT 1,
  saturday_rule VARCHAR(30) NOT NULL DEFAULT 'ALL', 
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
);

INSERT INTO weekly_off_config(sunday_off, saturday_rule)
VALUES(1, 'ALL');


CREATE TABLE payroll_run (
  id BIGINT NOT NULL AUTO_INCREMENT,
  year INT NOT NULL,
  month INT NOT NULL,
  month_days INT NOT NULL,
  created_by BIGINT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  UNIQUE KEY uq_run (year, month)
);

CREATE TABLE payroll_slip (
  id BIGINT NOT NULL AUTO_INCREMENT,
  payroll_run_id BIGINT NOT NULL,
  employee_id BIGINT NOT NULL,

  present_days INT NOT NULL,
  weekly_off_days INT NOT NULL,
  holiday_days INT NOT NULL,
  absent_days INT NOT NULL,

  per_day_salary DECIMAL(10,2) NOT NULL,
  absent_deduction DECIMAL(10,2) NOT NULL,
  payable_gross DECIMAL(10,2) NOT NULL,

  pf_amount DECIMAL(10,2) NOT NULL,
  esi_amount DECIMAL(10,2) NOT NULL,
  pt_amount DECIMAL(10,2) NOT NULL,

  net_salary DECIMAL(10,2) NOT NULL,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY(id),
  UNIQUE KEY uq_run_emp (payroll_run_id, employee_id),

  CONSTRAINT fk_slip_run FOREIGN KEY(payroll_run_id)
      REFERENCES payroll_run(id) ON DELETE CASCADE,

  CONSTRAINT fk_slip_emp FOREIGN KEY(employee_id)
      REFERENCES employees(employee_id) ON DELETE CASCADE
);


------------------------------------------------------------------------------


USE ems_db;
select * from employees;
select * from employee_face;

desc table employee_face;

select * from qt_table; 
select * from attendance_records;

delete from attendance_records where employee_mobile in ('9876543210','9000000001');

SELECT * 
FROM attendance_records 
WHERE employee_id = 1003 AND attendance_date = CURDATE();

DELETE FROM attendance_records 
WHERE employee_id = 1001 AND attendance_date = CURDATE();



select * from payroll_config;

select * from holiday_calendar;

select * from weekly_off_config;

select * from payroll_run;

select * from payroll_slip;

























