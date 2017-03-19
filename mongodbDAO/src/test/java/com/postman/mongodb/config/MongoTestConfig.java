package com.postman.mongodb.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by antonsakhno on 19.02.17.
 */
@Configuration
@ComponentScan(basePackages = {"com.postman.mongodb.impl"})

public class MongoTestConfig {

    @Bean
    public MongoClient mongoClient() {
        System.setProperty("mongoDBName", "testDB");
        return new Fongo("test").getMongo();
    }
}
