package com.armoury.repository;

import com.armoury.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    List<Weapon> findByIsAvailableTrue();
    List<Weapon> findByCategoryId(Long categoryId);
    Optional<Weapon> findBySerialNumber(String serialNumber);
}
