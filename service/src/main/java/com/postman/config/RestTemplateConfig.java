package com.postman.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Configuration
public class RestTemplateConfig {

//    @Bean
//    public RestTemplate restTemplate(){
//        RestTemplate restTemplate = new RestTemplate();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        messageConverters.add(converter);
//        restTemplate.setMessageConverters(messageConverters);
//        return restTemplate;
//    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setObjectMapper(new ObjectMapper());
                List<MediaType> mediaTypes = new ArrayList<>();
                mediaTypes.add(MediaType.TEXT_HTML);
                mediaTypes.add(MediaType.APPLICATION_JSON);
//                mediaTypes.add(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
//                mediaTypes.add(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
                jsonConverter.setSupportedMediaTypes(mediaTypes);
            }
        }
        return restTemplate;
    }
}
