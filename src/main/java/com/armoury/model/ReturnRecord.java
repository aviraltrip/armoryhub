package com.armoury.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "return_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "issuance_record_id", nullable = false)
    private IssuanceRecord issuanceRecord;

    @Column(nullable = false)
    private LocalDateTime returnDate;

    @Column(nullable = false)
    private String conditionStatus; // e.g. EXCELLENT, NEED_CLEANING, DAMAGED
}
