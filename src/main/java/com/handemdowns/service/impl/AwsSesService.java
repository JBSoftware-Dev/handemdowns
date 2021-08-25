package com.handemdowns.service.impl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailResult;
import com.handemdowns.persistence.dto.AdSendEmailDto;
import com.handemdowns.persistence.dto.ContactUsDto;
import com.handemdowns.persistence.model.Ad;
import com.handemdowns.persistence.model.Image;
import com.handemdowns.persistence.model.User;
import com.handemdowns.service.IApplicationService;
import com.handemdowns.service.IEmailService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

@Service
public class AwsSesService implements IEmailService {
    private static final String FROM_ADDRESS = "noreply@handemdowns.com";
    private static final String FROM_PERSONAL = "Handemdowns";

    private AmazonSimpleEmailServiceClient amazonSesClient;
    private IApplicationService applicationService;
    private TemplateEngine templateEngine;
    private ResourceLoader resourceLoader;

	@Autowired
	public AwsSesService(AmazonSimpleEmailServiceClient amazonSesClient, TemplateEngine templateEngine, IApplicationService applicationService, ResourceLoader resourceLoader) {
		this.amazonSesClient = amazonSesClient;
		this.templateEngine = templateEngine;
		this.applicationService = applicationService;
		this.resourceLoader = resourceLoader;
	}

	@Override
    public void sendUserRegistrationEmail(User user, String token) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("serverUrl", applicationService.getServerUrl());
        context.setVariable("user", user);
        context.setVariable("token", token);

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo(user.getEmail());
        message.setFrom(FROM_ADDRESS, FROM_PERSONAL);
        message.setSubject("Handemdowns Account Verification");
        message.setText(templateEngine.process("mail/email-registration", context), true);
        attachCommonImages(message);

        sendRawEmail(mimeMessage);
    }

    @Override
    public void sendResetPasswordEmail(User user, String token) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("serverUrl", applicationService.getServerUrl());
        context.setVariable("user", user);
        context.setVariable("token", token);

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo(user.getEmail());
        message.setFrom(FROM_ADDRESS, FROM_PERSONAL);
        message.setSubject("Handemdowns Password Reset");
        message.setText(templateEngine.process("mail/email-reset-password", context), true);
        attachCommonImages(message);

        sendRawEmail(mimeMessage);
    }

    @Override
    public void sendAdConfirmationEmail(Ad ad) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("serverUrl", applicationService.getServerUrl());
        context.setVariable("ad", ad);

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo(ad.getEmail());
        message.setFrom(FROM_ADDRESS, FROM_PERSONAL);
        message.setSubject("Your Handemdowns ad \"" + ad.getTitle() + "\" requires confirmation");
        message.setText(templateEngine.process("mail/email-ad-confirmation", context), true);
        attachCommonImages(message);
        attachAdImage(message, ad.getFirstImage());

        sendRawEmail(mimeMessage);
    }

    @Override
    public void sendAdActiveEmail(Ad ad) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("serverUrl", applicationService.getServerUrl());
        context.setVariable("ad", ad);

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo(ad.getEmail() == null ? ad.getUser().getEmail() : ad.getEmail());
        message.setFrom(FROM_ADDRESS, FROM_PERSONAL);
        message.setSubject("Your Handemdowns Ad \"" + ad.getTitle() + "\" is now active!");
        message.setText(templateEngine.process("mail/email-ad-active", context), true);
        attachCommonImages(message);
        attachAdImage(message, ad.getFirstImage());

        sendRawEmail(mimeMessage);
    }

    @Override
    public void sendAdReplyEmail(Ad ad, AdSendEmailDto adSendEmailDto) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("serverUrl", applicationService.getServerUrl());
        context.setVariable("ad", ad);
        context.setVariable("sender", adSendEmailDto);

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo(ad.getUser() == null ? ad.getEmail() : ad.getUser().getEmail());
        message.setFrom(FROM_ADDRESS, FROM_PERSONAL);
        message.setSubject("Reply to your ad \"" + ad.getTitle() + "\"");
        message.setText(templateEngine.process("mail/email-ad-reply", context), true);
        message.setReplyTo(adSendEmailDto.getEmail());
        if (adSendEmailDto.getSendCopy() != null && adSendEmailDto.getSendCopy()) message.setCc(adSendEmailDto.getEmail());
        attachCommonImages(message);

        sendRawEmail(mimeMessage);
    }

    @Override
    public void sendContactUsEmail(ContactUsDto contactUsDto) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("serverUrl", applicationService.getServerUrl());
        context.setVariable("sender", contactUsDto);

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo("help@handemdowns.com");
        message.setFrom(FROM_ADDRESS, FROM_PERSONAL);
        message.setSubject("Handemdowns Customer Feedback");
        message.setText(templateEngine.process("mail/email-contact-us", context), true);
        attachCommonImages(message);

        sendRawEmail(mimeMessage);
    }

    @Override
    public void sendTestEmail(String recipientEmail) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("serverUrl", applicationService.getServerUrl());

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setTo(recipientEmail);
        message.setFrom(FROM_ADDRESS, FROM_PERSONAL);
        message.setSubject("Handemdowns Test Email");
        message.setText(templateEngine.process("mail/email-test", context), true);
        attachCommonImages(message);

        Resource resource = resourceLoader.getResource("classpath:/static/images/ad_placeholder.png");
        InputStreamSource inputStreamSource = new ByteArrayResource(IOUtils.toByteArray(resource.getInputStream()));
        message.addInline("testImage", inputStreamSource, "image/png");

        sendRawEmail(mimeMessage);
    }

    private MimeMessageHelper attachCommonImages(MimeMessageHelper message) throws MessagingException, IOException {
        Resource resource;
        InputStreamSource inputStreamSource;

        resource = resourceLoader.getResource("classpath:/static/images/logo.png");
        inputStreamSource = new ByteArrayResource(IOUtils.toByteArray(resource.getInputStream()));
        message.addInline("siteLogo", inputStreamSource, "image/png");

        resource = resourceLoader.getResource("classpath:/static/images/social_facebook.png");
        inputStreamSource = new ByteArrayResource(IOUtils.toByteArray(resource.getInputStream()));
        message.addInline("facebookLogo", inputStreamSource, "image/png");

        resource = resourceLoader.getResource("classpath:/static/images/social_twitter.png");
        inputStreamSource = new ByteArrayResource(IOUtils.toByteArray(resource.getInputStream()));
        message.addInline("twitterLogo", inputStreamSource, "image/png");

        resource = resourceLoader.getResource("classpath:/static/images/social_googleplus.png");
        inputStreamSource = new ByteArrayResource(IOUtils.toByteArray(resource.getInputStream()));
        message.addInline("googleplusLogo", inputStreamSource, "image/png");

        return message;
    }

    private MimeMessageHelper attachAdImage(MimeMessageHelper message, Image adImage) throws MessagingException, IOException {
        Resource resource;
        InputStreamSource inputStreamSource;

        if (adImage == null) {
            resource = resourceLoader.getResource("classpath:/static/images/logo_no_text.png");
            inputStreamSource = new ByteArrayResource(IOUtils.toByteArray(resource.getInputStream()));
            message.addInline("adImage", inputStreamSource, "image/png");
        } else {
            resource = resourceLoader.getResource("url:" + applicationService.getAdImageUrl() + adImage.getFilename() + "_t1." + adImage.getExtension());
            inputStreamSource = new ByteArrayResource(IOUtils.toByteArray(resource.getInputStream()));
            message.addInline("adImage", inputStreamSource, adImage.getContentType());
        }

        return message;
    }

    private SendRawEmailResult sendRawEmail(MimeMessage mimeMessage) throws IOException, MessagingException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mimeMessage.writeTo(outputStream);
        RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

        SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);
        return amazonSesClient.sendRawEmail(rawEmailRequest);
    }
}