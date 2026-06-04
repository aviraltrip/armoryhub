package com.armoury.service;

import com.armoury.model.Officer;
import com.armoury.repository.OfficerRepository;
import com.armoury.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficerService {

    private final OfficerRepository officerRepository;
    private final AuditLogService auditLogService;

    @Autowired
    public OfficerService(OfficerRepository officerRepository, AuditLogService auditLogService) {
        this.officerRepository = officerRepository;
        this.auditLogService = auditLogService;
    }

    public Officer registerOfficer(Officer officer) {
        officer.setRole("OFFICER");
        java.util.Optional<Officer> existing = officerRepository.findByBadgeNumber(officer.getBadgeNumber());
        if (existing.isPresent()) {
            return existing.get();
        }
        Officer saved = officerRepository.save(officer);
        auditLogService.logAction("REGISTER_OFFICER", "Registered officer: " + saved.getName() + " (Badge: " + saved.getBadgeNumber() + ")", "ADMIN");
        return saved;
    }

    public List<Officer> getAllOfficers() {
        return officerRepository.findAll();
    }

    public Officer getOfficerById(Long id) {
        return officerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Officer not found with ID: " + id));
    }

    public Officer getOfficerByBadgeNumber(String badgeNumber) {
        return officerRepository.findByBadgeNumber(badgeNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Officer not found with badge number: " + badgeNumber));
    }
}
