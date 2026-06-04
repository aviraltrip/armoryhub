package com.armoury;

import com.armoury.model.*;
import com.armoury.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class ArmourySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArmourySystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            WeaponCategoryRepository categoryRepository,
            WeaponRepository weaponRepository,
            OfficerRepository officerRepository,
            AdminRepository adminRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                // Initialize Categories
                WeaponCategory rifle = new WeaponCategory(null, "Rifle", "Shoulder-fired long guns (e.g., M4, AK-47)");
                WeaponCategory pistol = new WeaponCategory(null, "Pistol", "Handheld short guns (e.g., Glock, Sig Sauer)");
                WeaponCategory shotgun = new WeaponCategory(null, "Shotgun", "Smoothbore long guns (e.g., Remington 870)");
                WeaponCategory sniper = new WeaponCategory(null, "Sniper", "High-precision long-range rifles (e.g., Barrett M82)");
                
                categoryRepository.saveAll(Arrays.asList(rifle, pistol, shotgun, sniper));

                // Initialize Weapons
                weaponRepository.save(new Weapon(null, "W101", "M4 Carbine", rifle, true, "OPERATIONAL"));
                weaponRepository.save(new Weapon(null, "W102", "Glock 19", pistol, true, "OPERATIONAL"));
                weaponRepository.save(new Weapon(null, "W103", "Remington 870", shotgun, true, "OPERATIONAL"));
                weaponRepository.save(new Weapon(null, "W104", "Barrett M82", sniper, true, "OPERATIONAL"));
                weaponRepository.save(new Weapon(null, "W105", "SIG Sauer P320", pistol, true, "MAINTENANCE"));

                // Initialize Officers
                officerRepository.save(new Officer("John Doe", "john.doe@police.gov", "password123", "B101", "Sergeant"));
                officerRepository.save(new Officer("Jane Smith", "jane.smith@police.gov", "password123", "B102", "Lieutenant"));
                officerRepository.save(new Officer("Bob Johnson", "bob.johnson@police.gov", "password123", "B103", "Officer"));

                // Initialize Admin
                adminRepository.save(new Admin("Admin Chief", "chief@police.gov", "adminpassword", "A001"));
                
                System.out.println("--- Armoury Management System Initial Sample Data Loaded Successfully ---");
            }
        };
    }
}
