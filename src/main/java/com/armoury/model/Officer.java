package com.armoury.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "officers")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class Officer extends User {
    
    @Column(nullable = false, unique = true)
    private String badgeNumber;
    
    @Column(name = "officer_rank", nullable = false)
    private String rank;

    public Officer(String name, String email, String password, String badgeNumber, String rank) {
        super(null, name, email, password, "OFFICER");
        this.badgeNumber = badgeNumber;
        this.rank = rank;
    }
}
