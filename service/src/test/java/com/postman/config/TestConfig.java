package com.postman.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Configuration
@ComponentScan("com.postman")
public class TestConfig {

    @Bean
    public ShaPasswordEncoder shaPasswordEncoder(){
        return new ShaPasswordEncoder();
    }
}
