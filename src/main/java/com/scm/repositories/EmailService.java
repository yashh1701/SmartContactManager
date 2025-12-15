package com.scm.repositories;

import org.springframework.stereotype.Component;

@Component
public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailWithHtml();
    
    void sendEmailWithAttachment();
}
