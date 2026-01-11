package com.attendance_service.service;

import com.attendance_service.repository.QrCodeRepository;
import com.attendance_service.util.QrDecoderUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class QrValidationService {

    private final QrCodeRepository qrRepo;

    public QrValidationService(QrCodeRepository qrRepo) {
        this.qrRepo = qrRepo;
    }

    public ValidationResult validate(String qrCodeValue) {

        QrDecoderUtil.validate(qrCodeValue);

        LocalDate serverToday = LocalDate.now();
        LocalDate qrDate = QrDecoderUtil.extractDate(qrCodeValue);

        if (!qrDate.equals(serverToday)) {
            return ValidationResult.invalid(qrDate, "QR date mismatch with server date");
        }

        boolean found = qrRepo.findByQrDateAndQrCodeValue(serverToday, qrCodeValue).isPresent();
        if (!found) {
            return ValidationResult.invalid(qrDate, "QR not found or inactive");
        }

        return ValidationResult.valid(qrDate);
    }

    public record ValidationResult(boolean valid, LocalDate qrDate, String reason) {
        public static ValidationResult valid(LocalDate date) { return new ValidationResult(true, date, null); }
        public static ValidationResult invalid(LocalDate date, String reason) { return new ValidationResult(false, date, reason); }
    }
}
