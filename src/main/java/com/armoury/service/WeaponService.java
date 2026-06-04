package com.armoury.service;

import com.armoury.model.Weapon;
import com.armoury.repository.WeaponRepository;
import com.armoury.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponService {

    private final WeaponRepository weaponRepository;
    private final AuditLogService auditLogService;

    @Autowired
    public WeaponService(WeaponRepository weaponRepository, AuditLogService auditLogService) {
        this.weaponRepository = weaponRepository;
        this.auditLogService = auditLogService;
    }

    public Weapon addWeapon(Weapon weapon) {
        java.util.Optional<Weapon> existing = weaponRepository.findBySerialNumber(weapon.getSerialNumber());
        if (existing.isPresent()) {
            return existing.get();
        }
        Weapon saved = weaponRepository.save(weapon);
        auditLogService.logAction("ADD_WEAPON", "Added weapon: " + saved.getName() + " (S/N: " + saved.getSerialNumber() + ")", "ADMIN");
        return saved;
    }

    public List<Weapon> getAllWeapons() {
        return weaponRepository.findAll();
    }

    public List<Weapon> getAvailableWeapons() {
        return weaponRepository.findByIsAvailableTrue();
    }

    public List<Weapon> getWeaponsByCategory(Long categoryId) {
        return weaponRepository.findByCategoryId(categoryId);
    }

    public Weapon getWeaponById(Long id) {
        return weaponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weapon not found with ID: " + id));
    }
    
    public Weapon save(Weapon weapon) {
        return weaponRepository.save(weapon);
    }
}
