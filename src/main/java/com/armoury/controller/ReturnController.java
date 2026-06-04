package com.armoury.controller;

import com.armoury.model.ReturnRecord;
import com.armoury.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/return")
public class ReturnController {

    private final ReturnService returnService;

    @Autowired
    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    public static class ReturnRequest {
        private Long issuanceRecordId;
        private String conditionStatus = "EXCELLENT";

        public Long getIssuanceRecordId() { return issuanceRecordId; }
        public void setIssuanceRecordId(Long issuanceRecordId) { this.issuanceRecordId = issuanceRecordId; }
        public String getConditionStatus() { return conditionStatus; }
        public void setConditionStatus(String conditionStatus) { this.conditionStatus = conditionStatus; }
    }

    @PostMapping
    public ResponseEntity<ReturnRecord> returnWeapon(@RequestBody ReturnRequest request) {
        ReturnRecord record = returnService.returnWeapon(
                request.getIssuanceRecordId(),
                request.getConditionStatus()
        );
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }
}
