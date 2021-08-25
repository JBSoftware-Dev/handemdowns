package com.handemdowns.event.listener;

import com.handemdowns.event.OnAnonymousAdCreatedEvent;
import com.handemdowns.persistence.model.SystemNotification;
import com.handemdowns.persistence.model.SystemNotificationType;
import com.handemdowns.persistence.service.ISystemNotificationService;
import com.handemdowns.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class OnAnonymousAdCreatedListener implements ApplicationListener<OnAnonymousAdCreatedEvent> {
	private final Logger log = LoggerFactory.getLogger(getClass());

    private IEmailService emailService;
	private ISystemNotificationService systemNotificationService;

	@Autowired
	public OnAnonymousAdCreatedListener(IEmailService emailService, ISystemNotificationService systemNotificationService) {
		this.emailService = emailService;
		this.systemNotificationService = systemNotificationService;
	}

	@Override
    public void onApplicationEvent(OnAnonymousAdCreatedEvent event) {
        try {
			log.info("Sending ad confirmation email for Ad: {}", event.getAd());
            emailService.sendAdConfirmationEmail(event.getAd());
        } catch (MessagingException | IOException e) {
            log.error(e.toString());
			systemNotificationService.createNotification(SystemNotification.builder(SystemNotificationType.CRITICAL_ERROR, e.getMessage()).build());
        }
    }
}