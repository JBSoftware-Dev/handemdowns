package com.handemdowns.service;

import com.handemdowns.persistence.dto.AdSendEmailDto;
import com.handemdowns.persistence.dto.ContactUsDto;
import com.handemdowns.persistence.model.Ad;
import com.handemdowns.persistence.model.User;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IEmailService {
    void sendUserRegistrationEmail(User user, String token) throws MessagingException, IOException;
    void sendResetPasswordEmail(User user, String token) throws MessagingException, IOException;
    void sendAdConfirmationEmail(Ad ad) throws MessagingException, IOException;
    void sendAdActiveEmail(Ad ad) throws MessagingException, IOException;
    void sendAdReplyEmail(Ad ad, AdSendEmailDto adSendEmailDto) throws MessagingException, IOException;
    void sendContactUsEmail(ContactUsDto contactUsDto) throws MessagingException, IOException;
    void sendTestEmail(String recipientEmail) throws MessagingException, IOException;
}