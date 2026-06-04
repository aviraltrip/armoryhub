package com.armoury.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class Admin extends User {
    
    @Column(nullable = false, unique = true)
    private String employeeId;

    public Admin(String name, String email, String password, String employeeId) {
        super(null, name, email, password, "ADMIN");
        this.employeeId = employeeId;
    }
}
