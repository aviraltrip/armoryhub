package com.armoury.controller;

import com.armoury.model.Notification;
import com.armoury.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/officer/{officerId}")
    public ResponseEntity<List<Notification>> getNotificationsForOfficer(@PathVariable Long officerId) {
        return ResponseEntity.ok(notificationService.getNotificationsForOfficer(officerId));
    }

    @GetMapping("/officer/{officerId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotificationsForOfficer(@PathVariable Long officerId) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationsForOfficer(officerId));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}
