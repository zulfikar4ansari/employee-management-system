package com.payroll_service.service;

import com.payroll_service.entity.PayrollConfigEntity;
import org.springframework.stereotype.Service;

@Service
public class PayrollCalculationService {

    public double perDaySalary(double grossSalary, int monthDays) {
        return grossSalary / monthDays;
    }

    public double absentDeduction(double perDaySalary, int absentDays) {
        return perDaySalary * absentDays;
    }

    public double pfAmount(PayrollConfigEntity cfg) {
        if (cfg.getPfEnabled() == null || !cfg.getPfEnabled()) return 0;
        return (cfg.getBasic() * cfg.getPfPercent()) / 100.0;
    }

    public double ptAmount(PayrollConfigEntity cfg) {
        return cfg.getPtAmount() == null ? 0 : cfg.getPtAmount();
    }
}
