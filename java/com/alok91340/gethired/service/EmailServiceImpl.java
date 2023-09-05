/**
 * 
 */
package com.alok91340.gethired.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

/*
 *
 * @author aloksingh
 *
 */
@Service
public class EmailServiceImpl {
	private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("HireQuest");
        message.setFrom(text);
        mailSender.send(message);
    }
}
