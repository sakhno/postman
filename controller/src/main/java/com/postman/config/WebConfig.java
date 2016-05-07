package com.postman.config;

import com.postman.gwt.client.TracksManageService;
import com.postman.gwt.server.GwtRpcController;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Configuration
@EnableWebMvc
@EnableScheduling
@EnableAspectJAutoProxy
@ComponentScan("com.postman")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private DebugInterceptor debugInterceptor;
    @Autowired
    private TracksManageService tracksManageService;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/com.postman.gwt.Postmangwt/**").addResourceLocations("/com.postman.gwt.Postmangwt/");
    }

    @Bean
    public SimpleUrlHandlerMapping GWTUrlHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MAX_VALUE - 2);

        Properties urlProperties = new Properties();
        //TODO значит нужно замапить все имена на этот контроллер
        //и в нем сделать массив контроллеров для обработки запросов
        urlProperties.put("/**/postmanGwtServices/tracksManageService", "quoteController");
        mapping.setMappings(urlProperties);
        return mapping;
    }

    @Bean(name = "quoteController")
    public GwtRpcController gwtRpcController() {
        GwtRpcController controller = new GwtRpcController();
        controller.setRemoteService(tracksManageService);
        return controller;
    }

    @Bean
    public DozerBeanMapperFactoryBean configDozer() throws IOException {
        DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:dozer-mapping.xml");
        mapper.setMappingFiles(resources);
        return mapper;
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver sessionLocaleResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        return localeResolver;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(debugInterceptor);
    }

}
