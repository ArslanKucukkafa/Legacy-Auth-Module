package com.arslankucukkafa.labormarketauth.util.email;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    void sendAuthenticateEmail(String targetEmail,String authenticateCode);

    @Async
    void sendConfirmationLinkEmail(String targetEmail, String authenticateCode);

    @Async
    void sendInfoEmail(String targetEmail,String title, String content);

    @Async
    void sendWarningEmail(String targetEmail,String title, String content);
}
