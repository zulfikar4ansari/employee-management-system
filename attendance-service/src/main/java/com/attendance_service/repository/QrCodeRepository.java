package com.attendance_service.repository;

import com.attendance_service.entity.QrCodeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface QrCodeRepository extends JpaRepository<QrCodeEntity, Long> {

    Optional<QrCodeEntity> findByQrDateAndQrCodeValue(LocalDate qrDate, String qrCodeValue);
}
