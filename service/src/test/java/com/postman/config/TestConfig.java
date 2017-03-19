package com.postman.config;

import com.postman.*;
import com.postman.aftership.ASTrackingRepository;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Configuration
@ComponentScan(basePackages = {"com.postman.impl"})
public class TestConfig {

    @Bean
    public ShaPasswordEncoder shaPasswordEncoder() {
        return new ShaPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        return Mockito.mock(JavaMailSender.class);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean(name = {"mongoUserDAO"})
    public UserDAO userDAO() {
        return Mockito.mock(UserDAO.class);
    }

    @Bean(name = {"mongoTrackDAO"})
    public TrackDAO trackDAO() {
        return Mockito.mock(TrackDAO.class);
    }

    @Bean(name = "mongoPostServiceDAO")
    public PostServiceDAO postServiceDAO() {
        return Mockito.mock(PostServiceDAO.class);
    }

    @Bean(name = "mongoMessageDAO")
    public MessageDAO MessageDAO() {
        return Mockito.mock(MessageDAO.class);
    }

    @Bean
    public TrackingService trackingService() {
        return Mockito.mock(TrackingService.class);
    }

    @Bean
    public ASTrackingRepository trackingRepository() {
        return Mockito.mock(ASTrackingRepository.class);
    }

    @Bean(name = {"mongoVerificationTokenDAO"})
    public VerificationTokenDAO verificationTokenDAO() {
        return Mockito.mock(VerificationTokenDAO.class);
    }
}
