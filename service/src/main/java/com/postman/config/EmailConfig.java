package com.postman.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Configuration
public class EmailConfig {

    @Bean
    public MailSender mailSender(){
        return null;
    }
}
