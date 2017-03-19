package com.postman.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Configuration
public class EmailConfig {
    private static final Logger LOGGER = LogManager.getLogger(EmailConfig.class);
    private static final String MAIL_CONFIG = "mailservice_config.properties";
    private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
    private static final int SMTP_PORT = 587;

//    @Bean
//    @Profile("default")
//    public Session mailSession() {
//        Properties props = new Properties();
//        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(MAIL_CONFIG)) {
//            props.load(inputStream);
//        } catch (IOException e) {
//            LOGGER.error(e);
//        }
//        final String userName = props.getProperty("mail.username");
//        final String password = props.getProperty("mail.password");
//        Session session = Session.getDefaultInstance(generalMailProperties(), new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(userName, password);
//            }
//        });
//        return session;
//    }
//
//    @Bean
//    @Profile("heroku")
//    public Session herokuMailSession() {
//        Session session = Session.getDefaultInstance(generalMailProperties(), new Authenticator() {
//            private String userName = System.getenv("SENDGRID_USERNAME");
//            private String password = System.getenv("SENDGRID_PASSWORD");
//
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(userName, password);
//            }
//        });
//        return session;
//    }

    @Bean
    public JavaMailSender mailSender() {
        Properties props = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(MAIL_CONFIG)) {
            props.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setUsername(props.getProperty("mail.username"));
        sender.setPassword(props.getProperty("mail.password"));
        sender.setJavaMailProperties(generalMailProperties());
        return sender;
    }

    @Bean
    @Profile("heroku")
    public JavaMailSender herokuMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setUsername(System.getenv("SENDGRID_USERNAME"));
        sender.setPassword(System.getenv("SENDGRID_PASSWORD"));
        sender.setJavaMailProperties(generalMailProperties());
        return sender;
    }

    private Properties generalMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.port", SMTP_PORT);
        return props;
    }

}
