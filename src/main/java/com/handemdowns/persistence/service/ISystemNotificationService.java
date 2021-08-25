package com.handemdowns.persistence.service;

import com.handemdowns.persistence.model.SystemNotification;

import java.util.List;

public interface ISystemNotificationService {
    void createNotification(SystemNotification systemNotification);

    List<SystemNotification> getLatestNotifications();
}