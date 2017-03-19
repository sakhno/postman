package com.postman.mongodb.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @author Anton Sakhno <antonsakhno.work@gmail.com>
 */
@Configuration
@ComponentScan("com.postman.impl")
public class MongoDBConfig {
    private static final Logger LOGGER = LogManager.getLogger(MongoDBConfig.class);
    private static final String PROPERTY_FILE_NAME = "mongodb_config.properties";

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(mongoClientURI());
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {

        return new SimpleMongoDbFactory(mongoClientURI());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory());
    }

    private MongoClientURI mongoClientURI() {
        String uri = System.getenv("MONGODB_URI");
        if (uri == null) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {
                Properties prop = new Properties();
                prop.load(inputStream);
                uri = prop.getProperty("mongodb.uri");
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        System.setProperty("mongoDBName", uri.substring(uri.lastIndexOf('/') + 1));
        return new MongoClientURI(uri);
    }
}
