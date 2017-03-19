package com.postman.aftership.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.postman.aftership.ASErrorDecoder;
import com.postman.aftership.ASTrackingRepository;
import feign.Feign;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * Created by antonsakhno on 19.02.17.
 */
@Configuration
public class TrackingRepositoryConfig {

    private static final Logger LOGGER = LogManager.getLogger(TrackingRepositoryConfig.class);

    private static final int CONNECTION_TIMEOUT = 1000;
    private static final int READ_TIMEOUT = 1000;
    private static final String API_URL = "https://api.aftership.com/v4";
    private static final String PROPERTY_FILE_NAME = "aftership.properties";

    @Bean
    public ASTrackingRepository trackingRepository() throws Exception {
        String apiKey = System.getProperty("aftership.api.key");
        if(apiKey == null) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {
                Properties prop = new Properties();
                prop.load(inputStream);
                apiKey = prop.getProperty("aftership.api.key");
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        String finalApiKey = apiKey;

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                ObjectCodec oc = p.getCodec();
                JsonNode node = oc.readTree(p);
//                2016-12-19T14:25:00+00:00
//                2011-12-03T10:15:30+01:00
                return LocalDateTime.parse(node.asText(), DateTimeFormatter.ISO_DATE_TIME);
            }
        });
        mapper.registerModule(module);
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder(mapper))
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate requestTemplate) {
                        requestTemplate.header("aftership-api-key", finalApiKey);
                    }
                })
                //.client(apacheHttpClient)
                .errorDecoder(new ASErrorDecoder())
                .logger(new Slf4jLogger())
                .logLevel(feign.Logger.Level.FULL)
                .options(new Request.Options(CONNECTION_TIMEOUT, READ_TIMEOUT))
                .target(ASTrackingRepository.class, API_URL);
    }
}
