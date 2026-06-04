package com.armoury.controller;

import com.armoury.model.Weapon;
import com.armoury.model.WeaponCategory;
import com.armoury.service.WeaponService;
import com.armoury.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weapons")
public class WeaponController {

    private final WeaponService weaponService;
    private final CategoryService categoryService;

    @Autowired
    public WeaponController(WeaponService weaponService, CategoryService categoryService) {
        this.weaponService = weaponService;
        this.categoryService = categoryService;
    }

    public static class WeaponRequest {
        private String serialNumber;
        private String name;
        private Long categoryId;
        private String status;

        public String getSerialNumber() { return serialNumber; }
        public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Long getCategoryId() { return categoryId; }
        public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @PostMapping
    public ResponseEntity<Weapon> addWeapon(@RequestBody WeaponRequest request) {
        WeaponCategory category = categoryService.getCategoryById(request.getCategoryId());
        Weapon weapon = new Weapon();
        weapon.setSerialNumber(request.getSerialNumber());
        weapon.setName(request.getName());
        weapon.setCategory(category);
        weapon.setAvailable(true);
        if (request.getStatus() != null) {
            weapon.setStatus(request.getStatus());
        } else {
            weapon.setStatus("OPERATIONAL");
        }
        Weapon saved = weaponService.addWeapon(weapon);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Weapon>> getAllWeapons() {
        return ResponseEntity.ok(weaponService.getAllWeapons());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Weapon>> getAvailableWeapons() {
        return ResponseEntity.ok(weaponService.getAvailableWeapons());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Weapon>> getWeaponsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(weaponService.getWeaponsByCategory(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weapon> getWeaponById(@PathVariable Long id) {
        return ResponseEntity.ok(weaponService.getWeaponById(id));
    }
}
