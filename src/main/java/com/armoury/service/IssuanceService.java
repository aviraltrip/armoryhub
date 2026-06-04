package com.armoury.service;

import com.armoury.model.*;
import com.armoury.repository.IssuanceRecordRepository;
import com.armoury.exception.WeaponNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssuanceService {

    private final IssuanceRecordRepository issuanceRecordRepository;
    private final WeaponService weaponService;
    private final OfficerService officerService;
    private final NotificationService notificationService;
    private final AuditLogService auditLogService;

    @Autowired
    public IssuanceService(
            IssuanceRecordRepository issuanceRecordRepository,
            WeaponService weaponService,
            OfficerService officerService,
            NotificationService notificationService,
            AuditLogService auditLogService) {
        this.issuanceRecordRepository = issuanceRecordRepository;
        this.weaponService = weaponService;
        this.officerService = officerService;
        this.notificationService = notificationService;
        this.auditLogService = auditLogService;
    }

    @Transactional
    public IssuanceRecord issueWeapon(Long officerId, Long weaponId, int expectedReturnDays) {
        Officer officer = officerService.getOfficerById(officerId);
        Weapon weapon = weaponService.getWeaponById(weaponId);

        if (!weapon.isAvailable()) {
            throw new WeaponNotAvailableException("Weapon is currently unavailable (Serial Number: " + weapon.getSerialNumber() + ")");
        }

        // Set weapon as unavailable
        weapon.setAvailable(false);
        weaponService.save(weapon);

        // Create Issuance Record
        IssuanceRecord record = new IssuanceRecord();
        record.setOfficer(officer);
        record.setWeapon(weapon);
        record.setIssueDate(LocalDateTime.now());
        record.setExpectedReturnDate(LocalDateTime.now().plusDays(expectedReturnDays));
        record.setStatus(IssuanceRecord.IssuanceStatus.ISSUED);

        IssuanceRecord savedRecord = issuanceRecordRepository.save(record);

        // Push alert via Notification (Observer Pattern)
        String notificationMessage = String.format("Weapon %s (S/N: %s) has been issued to you. Expected return: %s.",
                weapon.getName(), weapon.getSerialNumber(), record.getExpectedReturnDate().toLocalDate().toString());
        notificationService.createNotification(officer, notificationMessage);

        // Update Audit Log
        auditLogService.logAction("ISSUE_WEAPON",
                String.format("Issued %s (S/N: %s) to Officer %s (Badge: %s)",
                        weapon.getName(), weapon.getSerialNumber(), officer.getName(), officer.getBadgeNumber()),
                "ADMIN");

        return savedRecord;
    }

    public List<IssuanceRecord> getActiveIssuances() {
        return issuanceRecordRepository.findByStatus(IssuanceRecord.IssuanceStatus.ISSUED);
    }

    public IssuanceRecord getIssuanceById(Long id) {
        return issuanceRecordRepository.findById(id)
                .orElseThrow(() -> new com.armoury.exception.ResourceNotFoundException("Issuance record not found with ID: " + id));
    }
    
    public IssuanceRecord save(IssuanceRecord record) {
        return issuanceRecordRepository.save(record);
    }
}
