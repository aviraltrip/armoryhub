package com.armoury.repository;

import com.armoury.model.IssuanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssuanceRecordRepository extends JpaRepository<IssuanceRecord, Long> {
    List<IssuanceRecord> findByOfficerId(Long officerId);
    List<IssuanceRecord> findByStatus(IssuanceRecord.IssuanceStatus status);
    List<IssuanceRecord> findByOfficerIdAndStatus(Long officerId, IssuanceRecord.IssuanceStatus status);
    List<IssuanceRecord> findByWeaponIdAndStatus(Long weaponId, IssuanceRecord.IssuanceStatus status);
}
