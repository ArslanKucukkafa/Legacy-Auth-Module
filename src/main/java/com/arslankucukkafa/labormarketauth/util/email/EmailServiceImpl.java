package com.arslankucukkafa.labormarketauth.util.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final String InfoHtmlTemplate = "InfoEmail.html";
    private final String AuthenticateHtmlTemplate = "AuthenticateEmail.html";
    private final String WarningHtmlTemplate = "WarningEmail.html";
    private final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private JavaMailSender javaMailSender;
//    private EmailAuthConfigurationProperties emailAuthConfigurationProperties; EmailAuthConfigurationProperties emailAuthConfigurationProperties,
    private EmailTemplateService emailTemplateService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, EmailTemplateService emailTemplateService) {
        this.javaMailSender = javaMailSender;
        this.emailTemplateService = emailTemplateService;
    }

    @Override
    public void sendAuthenticateEmail(String targetEmail,String authenticateCode) {
        String templateContent = emailTemplateService.prepareAuthenticateEmailContent(AuthenticateHtmlTemplate,authenticateCode);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("arslan.kucukkafa@zohomail.eu"));
            message.setRecipients(MimeMessage.RecipientType.TO, targetEmail);
            message.setSubject("Authenticate Email");
            message.setContent(templateContent, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            LOGGER.error("Error while sending authenticate email to: " + targetEmail, e);
        }

        javaMailSender.send(message);
        LOGGER.info("Authenticate email sent to: " + targetEmail);
    }

    @Override
    public void sendConfirmationLinkEmail(String targetEmail, String url) {
        String templateContent = emailTemplateService.prepareGeneralEmailContent(InfoHtmlTemplate,"Confirmation Link", url);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("arslan.kucukkafa@zohomail.eu"));
            message.setRecipients(MimeMessage.RecipientType.TO, targetEmail);
            message.setSubject("Authenticate Email");
            message.setContent(templateContent, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            LOGGER.error("Error while sending authenticate email to: " + targetEmail, e);
        }

        javaMailSender.send(message);
        LOGGER.info("Authenticate email sent to: " + targetEmail);
    }

    @Override
    public void sendInfoEmail(String targetEmail,String title, String content) {
        String templateContent = emailTemplateService.prepareGeneralEmailContent(InfoHtmlTemplate,title,content);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("arslan.kucukkafa@zohomail.eu"));
            message.setRecipients(MimeMessage.RecipientType.TO, targetEmail);
            message.setSubject("Info Email");
            message.setContent(templateContent, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            LOGGER.error("Error while sending info email to: " + targetEmail, e);
        }

        javaMailSender.send(message);
        LOGGER.info("Info email sent to: " + targetEmail);
    }

    @Override
    public void sendWarningEmail(String targetEmail,String title, String content) {
        String templateContent = emailTemplateService.prepareGeneralEmailContent(WarningHtmlTemplate, title, content);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("arslan.kucukkafa@zohomail.eu"));
            message.setRecipients(MimeMessage.RecipientType.TO, targetEmail);
            message.setSubject("Warn Email");
            message.setContent(templateContent, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            LOGGER.error("Error while sending warning email to: " + targetEmail, e);
        }

        javaMailSender.send(message);

        LOGGER.info("Warning email sent to: " + targetEmail);
    }
}

