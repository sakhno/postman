package com.postman.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Configuration
public class EmailConfig {
    private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
    private static final int SMTP_PORT = 587;
    private static final String SMTP_AUTH_USER = System.getenv("SENDGRID_USERNAME");
    private static final String SMTP_AUTH_PWD  = System.getenv("SENDGRID_PASSWORD");

    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(SMTP_HOST_NAME);
        mailSender.setPort(SMTP_PORT);
        mailSender.setUsername(SMTP_AUTH_USER);
        mailSender.setPassword(SMTP_AUTH_PWD);
        mailSender.setDefaultEncoding("iso-8859-1");
        return mailSender;
    }
}
