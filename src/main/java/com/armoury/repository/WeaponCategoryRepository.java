package com.armoury.repository;

import com.armoury.model.WeaponCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WeaponCategoryRepository extends JpaRepository<WeaponCategory, Long> {
    Optional<WeaponCategory> findByName(String name);
}
