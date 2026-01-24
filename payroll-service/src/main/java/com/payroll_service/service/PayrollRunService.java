package com.payroll_service.service;

import com.payroll_service.client.EmployeeClient;
import com.payroll_service.dto.EmployeeDTO;
import com.payroll_service.dto.PayrollRunResponse;
import com.payroll_service.entity.*;
import com.payroll_service.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.Map;

@Service
public class PayrollRunService {

    private final EmployeeClient employeeClient;
    private final PayrollRunRepository runRepo;
    private final PayrollSlipRepository slipRepo;
    private final PayrollConfigRepository configRepo;
    private final WeeklyOffConfigRepository weeklyRepo;
    private final HolidayRepository holidayRepo;
    private final AttendanceStatsRepository attendanceRepo;
    private final PayrollCalculationService calc;

    public PayrollRunService(EmployeeClient employeeClient,
                             PayrollRunRepository runRepo,
                             PayrollSlipRepository slipRepo,
                             PayrollConfigRepository configRepo,
                             WeeklyOffConfigRepository weeklyRepo,
                             HolidayRepository holidayRepo,
                             AttendanceStatsRepository attendanceRepo,
                             PayrollCalculationService calc) {
        this.employeeClient = employeeClient;
        this.runRepo = runRepo;
        this.slipRepo = slipRepo;
        this.configRepo = configRepo;
        this.weeklyRepo = weeklyRepo;
        this.holidayRepo = holidayRepo;
        this.attendanceRepo = attendanceRepo;
        this.calc = calc;
    }

    @Transactional
    public PayrollRunResponse generatePayroll(Integer year, Integer month, Long adminEmployeeId) {

        runRepo.findByYearAndMonth(year, month).ifPresent(r -> {
            throw new RuntimeException("Payroll already generated for " + year + "-" + month);
        });

        YearMonth ym = YearMonth.of(year, month);
        int monthDays = ym.lengthOfMonth();
        LocalDate from = ym.atDay(1);
        LocalDate to = ym.atEndOfMonth();

        WeeklyOffConfigEntity weeklyCfg = weeklyRepo.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Weekly off config not found"));

        int weeklyOffDays = calculateWeeklyOffs(weeklyCfg, from, to);
        int holidayDays = countPaidHolidays(from, to);

        PayrollRunEntity run = new PayrollRunEntity();
        run.setYear(year);
        run.setMonth(month);
        run.setMonthDays(monthDays);
        run.setCreatedBy(adminEmployeeId);

        PayrollRunEntity savedRun = runRepo.save(run);

        // fetch employee list (admin endpoint from employee-service)
        //List<Map<String, Object>> employees = employeeClient.getAllEmployees();
        List<EmployeeDTO> employees = employeeClient.getAllEmployees();

        for (EmployeeDTO emp : employees) {

            Long empId = emp.getEmployeeId();

            PayrollConfigEntity cfg = configRepo.findById(empId)
                    .orElseThrow(() -> new RuntimeException("Payroll config not set for employee: " + empId));

            int presentDays = attendanceRepo.countPresentDays(empId, from, to);

            int paidDays = presentDays + weeklyOffDays + holidayDays;
            int absentDays = monthDays - paidDays;
            if (absentDays < 0) absentDays = 0;

            double perDay = calc.perDaySalary(cfg.getGrossSalary(), monthDays);
            double absentDeduction = calc.absentDeduction(perDay, absentDays);
            double payableGross = cfg.getGrossSalary() - absentDeduction;

            double pf = calc.pfAmount(cfg);
            double pt = calc.ptAmount(cfg);

            double net = payableGross - pf - pt;

            PayrollSlipEntity slip = new PayrollSlipEntity();
            slip.setPayrollRunId(savedRun.getId());
            slip.setEmployeeId(empId);

            slip.setPresentDays(presentDays);
            slip.setWeeklyOffDays(weeklyOffDays);
            slip.setHolidayDays(holidayDays);
            slip.setAbsentDays(absentDays);

            slip.setPerDaySalary(round(perDay));
            slip.setAbsentDeduction(round(absentDeduction));
            slip.setPayableGross(round(payableGross));

            slip.setPfAmount(round(pf));
            slip.setEsiAmount(0.0);
            slip.setPtAmount(round(pt));

            slip.setNetSalary(round(net));

            slipRepo.save(slip);
        }


        return new PayrollRunResponse(savedRun.getId(), year, month, monthDays);
    }

    private int calculateWeeklyOffs(WeeklyOffConfigEntity cfg, LocalDate from, LocalDate to) {

        int count = 0;
        int saturdayIndex = 0;

        for (LocalDate d = from; !d.isAfter(to); d = d.plusDays(1)) {

            if (cfg.getSundayOff() && d.getDayOfWeek() == DayOfWeek.SUNDAY) {
                count++;
                continue;
            }

            if (d.getDayOfWeek() == DayOfWeek.SATURDAY) {
                saturdayIndex++;

                if ("ALL".equalsIgnoreCase(cfg.getSaturdayRule())) count++;
                if ("NONE".equalsIgnoreCase(cfg.getSaturdayRule())) { }
                if ("2ND_4TH".equalsIgnoreCase(cfg.getSaturdayRule())) {
                    if (saturdayIndex == 2 || saturdayIndex == 4) count++;
                }
            }
        }
        return count;
    }

    private int countPaidHolidays(LocalDate from, LocalDate to) {
        List<HolidayEntity> list = holidayRepo.findAll();
        return (int) list.stream()
                .filter(h -> Boolean.TRUE.equals(h.getPaid()))
                .filter(h -> !h.getHolidayDate().isBefore(from) && !h.getHolidayDate().isAfter(to))
                .count();
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
