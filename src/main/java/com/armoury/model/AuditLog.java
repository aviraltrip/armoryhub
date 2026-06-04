package com.armoury.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action; // e.g. ADD_WEAPON, ISSUE_WEAPON, RETURN_WEAPON

    @Column(nullable = false, length = 1000)
    private String details;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String actor; // e.g. admin or officer username/email

    public AuditLog(String action, String details, String actor) {
        this.action = action;
        this.details = details;
        this.actor = actor;
        this.timestamp = LocalDateTime.now();
    }
}
