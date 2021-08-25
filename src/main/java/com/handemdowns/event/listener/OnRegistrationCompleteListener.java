package com.handemdowns.event.listener;

import com.handemdowns.event.OnRegistrationCompleteEvent;
import com.handemdowns.persistence.model.SystemNotification;
import com.handemdowns.persistence.model.SystemNotificationType;
import com.handemdowns.persistence.model.User;
import com.handemdowns.persistence.service.ISystemNotificationService;
import com.handemdowns.persistence.service.IUserService;
import com.handemdowns.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.UUID;

@Component
public class OnRegistrationCompleteListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	private final Logger log = LoggerFactory.getLogger(getClass());

    private IUserService userService;
    private IEmailService emailService;
	private ISystemNotificationService systemNotificationService;

	@Autowired
	public OnRegistrationCompleteListener(IUserService userService, IEmailService emailService, ISystemNotificationService systemNotificationService) {
		this.userService = userService;
		this.emailService = emailService;
		this.systemNotificationService = systemNotificationService;
	}

	@Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            User user = event.getUser();
            String token = UUID.randomUUID().toString();
            userService.createVerificationTokenForUser(user, token);
			log.info("Sending registration confirmation email for User: {}", user);
            emailService.sendUserRegistrationEmail(user, token);
		} catch (MessagingException | IOException e) {
			log.error(e.toString());
			systemNotificationService.createNotification(SystemNotification.builder(SystemNotificationType.CRITICAL_ERROR, e.getMessage()).build());
		}
    }
}