package com.armoury.controller;

import com.armoury.model.Officer;
import com.armoury.service.OfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/officers")
public class OfficerController {

    private final OfficerService officerService;

    @Autowired
    public OfficerController(OfficerService officerService) {
        this.officerService = officerService;
    }

    @PostMapping
    public ResponseEntity<Officer> registerOfficer(@RequestBody Officer officer) {
        Officer registered = officerService.registerOfficer(officer);
        return new ResponseEntity<>(registered, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Officer>> getAllOfficers() {
        return ResponseEntity.ok(officerService.getAllOfficers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Officer> getOfficerById(@PathVariable Long id) {
        return ResponseEntity.ok(officerService.getOfficerById(id));
    }
    
    @GetMapping("/badge/{badgeNumber}")
    public ResponseEntity<Officer> getOfficerByBadgeNumber(@PathVariable String badgeNumber) {
        return ResponseEntity.ok(officerService.getOfficerByBadgeNumber(badgeNumber));
    }
}
