package com.armoury.repository;

import com.armoury.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByOfficerIdOrderByTimestampDesc(Long officerId);
    List<Notification> findByOfficerIdAndIsReadFalse(Long officerId);
}
