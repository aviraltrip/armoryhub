package com.armoury.service;

import com.armoury.model.Notification;

public interface NotificationObserver {
    void onNotificationReceived(Notification notification);
}
