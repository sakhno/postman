package com.postman.impl;

import com.postman.MailService;
import com.postman.VerificationTokenDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Service
public class MailServiceImpl implements MailService{
    private final static Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);
    private static final String DEFAULT_FROM_ADDRESS = "service.postman@gmail.com";
    @Autowired
    private MailSender mailSender;

    @Override
    public boolean sendMail(String toAddress, String subject, String message) {
        return sendMail(toAddress, subject, message, DEFAULT_FROM_ADDRESS);
    }

    @Override
    public boolean sendMail(String toAddress, String subject, String message, String fromAddress) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom(fromAddress);
        mailSender.send(simpleMailMessage);
        return false;
    }

}
