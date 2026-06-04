package com.armoury.service;

import com.armoury.model.Notification;

public interface NotificationSubject {
    void registerObserver(NotificationObserver observer);
    void removeObserver(NotificationObserver observer);
    void notifyObservers(Notification notification);
}
