package com.armoury.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "issuance_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "officer_id", nullable = false)
    private Officer officer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "weapon_id", nullable = false)
    private Weapon weapon;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    @Column(nullable = false)
    private LocalDateTime expectedReturnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssuanceStatus status = IssuanceStatus.ISSUED; // ISSUED, RETURNED

    public enum IssuanceStatus {
        ISSUED,
        RETURNED
    }
}
