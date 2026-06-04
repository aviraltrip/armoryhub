package com.armoury.repository;

import com.armoury.model.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OfficerRepository extends JpaRepository<Officer, Long> {
    Optional<Officer> findByBadgeNumber(String badgeNumber);
    Optional<Officer> findByEmail(String email);
}
