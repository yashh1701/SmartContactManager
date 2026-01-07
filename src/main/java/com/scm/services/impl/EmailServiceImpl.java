package com.scm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.scm.services.EmailService;

import jakarta.mail.internet.MimeMessage;
 

@Service
public class EmailServiceImpl implements EmailService{

	
	@Autowired
    private JavaMailSender eMailSender;
    @Value("${spring.mail.from}")
    private String fromMail;

    
    @Override
    public void sendEmail(String to, String subject, String verificationLink) {

        try {
            MimeMessage message = eMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setFrom(fromMail);
            helper.setSubject(subject);

            String htmlBody ="""
            		<!DOCTYPE html>
            		<html>
            		<head>
            		    <meta charset="UTF-8">
            		    <title>Email Verification</title>
            		</head>
            		<body style="margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, Helvetica, sans-serif;">

            		<table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f8; padding:30px 0;">
            		    <tr>
            		        <td align="center">

            		            <!-- Main Card -->
            		            <table width="600" cellpadding="0" cellspacing="0"
            		                   style="background:#ffffff; border-radius:8px; box-shadow:0 4px 12px rgba(0,0,0,0.08); overflow:hidden;">

            		                <!-- Header -->
            		                <tr>
            		                    <td style="background:#2563eb; padding:24px; text-align:center;">
            		                        <h1 style="color:#ffffff; margin:0; font-size:22px;">
            		                            Smart Contact Manager
            		                        </h1>
            		                    </td>
            		                </tr>

            		                <!-- Body -->
            		                <tr>
            		                    <td style="padding:32px; color:#333333;">
            		                        <h2 style="margin-top:0;">Verify your email address</h2>

            		                        <p style="font-size:15px; line-height:1.6;">
            		                            Thank you for signing up. To complete your registration,
            		                            please confirm your email address by clicking the button below.
            		                        </p>

            		                        <!-- Button -->
            		                        <div style="text-align:center; margin:32px 0;">
            		                            <a href="%s"
            		                               style="background:#2563eb;
            		                                      color:#ffffff;
            		                                      padding:14px 28px;
            		                                      text-decoration:none;
            		                                      font-size:16px;
            		                                      border-radius:6px;
            		                                      display:inline-block;">
            		                                Verify Email
            		                            </a>
            		                        </div>

            		                        <p style="font-size:14px; color:#555555;">
            		                            If the button above does not work, copy and paste this link into your browser:
            		                        </p>

            		                        <p style="font-size:13px; color:#2563eb; word-break:break-all;">
            		                            %s
            		                        </p>

            		                        <p style="font-size:14px; color:#555555; margin-top:30px;">
            		                            This verification link will expire for security reasons.
            		                        </p>

            		                        <p style="font-size:14px; color:#555555;">
            		                            If you did not create an account, you can safely ignore this email.
            		                        </p>
            		                    </td>
            		                </tr>

            		                <!-- Footer -->
            		                <tr>
            		                    <td style="background:#f4f6f8; padding:20px; text-align:center; font-size:12px; color:#777777;">
            		                        © 2026 Smart Contact Manager<br>
            		                        All rights reserved.
            		                    </td>
            		                </tr>

            		            </table>

            		        </td>
            		    </tr>
            		</table>

            		</body>
            		</html>
            		""".formatted(verificationLink, verificationLink);

            helper.setText(htmlBody, true); // true = HTML

            eMailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    @Override
    public void sendEmailWithHtml() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithHtml'");
    }

    @Override
    public void sendEmailWithAttachment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithAttachment'");
    }
	
	
}
