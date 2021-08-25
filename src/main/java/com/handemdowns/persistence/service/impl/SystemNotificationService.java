package com.handemdowns.persistence.service.impl;

import com.handemdowns.persistence.dao.SystemNotificationRepository;
import com.handemdowns.persistence.model.SystemNotification;
import com.handemdowns.persistence.service.ISystemNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SystemNotificationService implements ISystemNotificationService {
    private SystemNotificationRepository repository;

	@Autowired
	public SystemNotificationService(SystemNotificationRepository repository) {
		this.repository = repository;
	}

	@Override
    public void createNotification(SystemNotification systemNotification) {
        repository.save(systemNotification);
    }

    @Override
    public List<SystemNotification> getLatestNotifications() {
        return repository.findFirst5ByOrderByCreateDateDesc();
    }
}