package com.postman.impl;

import com.postman.MailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class MailServiceImpl implements MailService{
    private final static Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);
    private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
    private static final String SMTP_AUTH_USER = System.getenv("SENDGRID_USERNAME");
    private static final String SMTP_AUTH_PWD  = System.getenv("SENDGRID_PASSWORD");
    private static final String DEFAULT_FROM_ADDRESS = "service.postman@gmail.com";

    @Override
    public boolean sendMail(String address, String message) {
        return false;
    }

    @Override
    public boolean sendMail(String address, String message, String from) {
        return false;
    }
}
