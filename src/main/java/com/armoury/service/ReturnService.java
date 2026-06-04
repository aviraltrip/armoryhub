package com.armoury.service;

import com.armoury.model.*;
import com.armoury.repository.ReturnRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReturnService {

    private final ReturnRecordRepository returnRecordRepository;
    private final IssuanceService issuanceService;
    private final WeaponService weaponService;
    private final AuditLogService auditLogService;
    private final NotificationService notificationService;

    @Autowired
    public ReturnService(
            ReturnRecordRepository returnRecordRepository,
            IssuanceService issuanceService,
            WeaponService weaponService,
            AuditLogService auditLogService,
            NotificationService notificationService) {
        this.returnRecordRepository = returnRecordRepository;
        this.issuanceService = issuanceService;
        this.weaponService = weaponService;
        this.auditLogService = auditLogService;
        this.notificationService = notificationService;
    }

    @Transactional
    public ReturnRecord returnWeapon(Long issuanceRecordId, String conditionStatus) {
        IssuanceRecord issuanceRecord = issuanceService.getIssuanceById(issuanceRecordId);

        if (issuanceRecord.getStatus() == IssuanceRecord.IssuanceStatus.RETURNED) {
            throw new IllegalStateException("This issuance record has already been marked as returned.");
        }

        // Restore availability of the weapon
        Weapon weapon = issuanceRecord.getWeapon();
        weapon.setAvailable(true);
        weaponService.save(weapon);

        // Update Issuance status
        issuanceRecord.setStatus(IssuanceRecord.IssuanceStatus.RETURNED);
        issuanceService.save(issuanceRecord);

        // Create Return Record
        ReturnRecord returnRecord = new ReturnRecord();
        returnRecord.setIssuanceRecord(issuanceRecord);
        returnRecord.setReturnDate(LocalDateTime.now());
        returnRecord.setConditionStatus(conditionStatus);
        ReturnRecord savedReturnRecord = returnRecordRepository.save(returnRecord);

        // Notify officer (Observer Pattern)
        String notificationMessage = String.format("Weapon %s (S/N: %s) has been returned successfully in %s condition.",
                weapon.getName(), weapon.getSerialNumber(), conditionStatus);
        notificationService.createNotification(issuanceRecord.getOfficer(), notificationMessage);

        // Update Audit Log
        auditLogService.logAction("RETURN_WEAPON",
                String.format("Officer %s (Badge: %s) returned %s (S/N: %s). Condition: %s",
                        issuanceRecord.getOfficer().getName(), issuanceRecord.getOfficer().getBadgeNumber(),
                        weapon.getName(), weapon.getSerialNumber(), conditionStatus),
                "ADMIN");

        return savedReturnRecord;
    }
}
