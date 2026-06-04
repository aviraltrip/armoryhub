package com.armoury.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "weapons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private WeaponCategory category;

    @Column(nullable = false)
    private boolean isAvailable = true;

    @Column(nullable = false)
    private String status = "OPERATIONAL"; // e.g. OPERATIONAL, MAINTENANCE, DECOMMISSIONED
}
