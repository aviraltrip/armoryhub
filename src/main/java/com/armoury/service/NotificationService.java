package com.armoury.service;

import com.armoury.model.Notification;
import com.armoury.model.Officer;
import com.armoury.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService implements NotificationSubject {

    private final NotificationRepository notificationRepository;
    private final List<NotificationObserver> observers = new ArrayList<>();

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public synchronized void registerObserver(NotificationObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public synchronized void removeObserver(NotificationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public synchronized void notifyObservers(Notification notification) {
        for (NotificationObserver observer : observers) {
            observer.onNotificationReceived(notification);
        }
    }

    public Notification createNotification(Officer officer, String message) {
        Notification notification = new Notification(officer, message);
        Notification savedNotification = notificationRepository.save(notification);
        notifyObservers(savedNotification);
        return savedNotification;
    }

    public List<Notification> getNotificationsForOfficer(Long officerId) {
        return notificationRepository.findByOfficerIdOrderByTimestampDesc(officerId);
    }

    public List<Notification> getUnreadNotificationsForOfficer(Long officerId) {
        return notificationRepository.findByOfficerIdAndIsReadFalse(officerId);
    }

    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }
}
