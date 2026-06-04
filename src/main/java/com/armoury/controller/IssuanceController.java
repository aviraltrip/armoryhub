package com.armoury.controller;

import com.armoury.model.IssuanceRecord;
import com.armoury.service.IssuanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issuance")
public class IssuanceController {

    private final IssuanceService issuanceService;

    @Autowired
    public IssuanceController(IssuanceService issuanceService) {
        this.issuanceService = issuanceService;
    }

    public static class IssuanceRequest {
        private Long officerId;
        private Long weaponId;
        private int expectedReturnDays = 7;

        public Long getOfficerId() { return officerId; }
        public void setOfficerId(Long officerId) { this.officerId = officerId; }
        public Long getWeaponId() { return weaponId; }
        public void setWeaponId(Long weaponId) { this.weaponId = weaponId; }
        public int getExpectedReturnDays() { return expectedReturnDays; }
        public void setExpectedReturnDays(int expectedReturnDays) { this.expectedReturnDays = expectedReturnDays; }
    }

    @PostMapping
    public ResponseEntity<IssuanceRecord> issueWeapon(@RequestBody IssuanceRequest request) {
        IssuanceRecord record = issuanceService.issueWeapon(
                request.getOfficerId(),
                request.getWeaponId(),
                request.getExpectedReturnDays()
        );
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @GetMapping("/active")
    public ResponseEntity<List<IssuanceRecord>> getActiveIssuances() {
        return ResponseEntity.ok(issuanceService.getActiveIssuances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssuanceRecord> getIssuanceById(@PathVariable Long id) {
        return ResponseEntity.ok(issuanceService.getIssuanceById(id));
    }
}
