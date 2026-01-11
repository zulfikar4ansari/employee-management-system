package com.attendance_service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class QrDecoderUtil {

    private QrDecoderUtil() {}

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static void validate(String qr) {
        if (qr == null || qr.isBlank()) throw new RuntimeException("QR is empty");
        if (!qr.matches("\\d{16}")) throw new RuntimeException("QR must be 16-digit numeric");
    }

    public static LocalDate extractDate(String qr) {
        return LocalDate.parse(qr.substring(0, 8), FMT);
    }
}
