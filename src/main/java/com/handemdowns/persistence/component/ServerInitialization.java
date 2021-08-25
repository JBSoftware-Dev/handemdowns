package com.handemdowns.persistence.component;

import com.handemdowns.persistence.model.SystemNotification;
import com.handemdowns.persistence.model.SystemNotificationType;
import com.handemdowns.persistence.service.ISystemNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServerInitialization {
    private ISystemNotificationService systemNotificationService;

	@Autowired
	public ServerInitialization(ISystemNotificationService systemNotificationService) {
		this.systemNotificationService = systemNotificationService;
	}

	@PostConstruct
    public void init() {
        systemNotificationService.createNotification(SystemNotification.builder(SystemNotificationType.SERVER_REBOOT, "Application startup").build());
    }
}