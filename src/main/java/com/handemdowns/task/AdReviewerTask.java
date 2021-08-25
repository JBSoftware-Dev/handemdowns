package com.handemdowns.task;

import com.handemdowns.persistence.model.Ad;
import com.handemdowns.persistence.model.Status;
import com.handemdowns.persistence.service.IAdService;
import com.handemdowns.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Component
public class AdReviewerTask {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private IAdService adService;
    private IEmailService emailService;

	@Autowired
	public AdReviewerTask(IAdService adService, IEmailService emailService) {
		this.adService = adService;
		this.emailService = emailService;
	}

	@Scheduled(fixedRate = 1000 * 30)
    public void activateAwaitingAds() throws IOException, MessagingException {
        List<Ad> awaitingAds = adService.findByStatus(Status.PENDING);
        for (Ad ad : awaitingAds) {
			log.info("Activating ad: {}", ad);
            adService.activate(ad);
            if (ad.getUser() == null) {
				log.info("Sending ad active email for Ad: {}", ad);
                emailService.sendAdActiveEmail(ad);
            }
        }
    }
}