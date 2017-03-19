package com.postman.hibernate.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    private static final Logger LOGGER = LogManager.getLogger(HibernateConfig.class);
    private static final String PROPERTY_FILE_NAME = "postgresql_config.properties";

    @Bean
    @Profile("default")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {
            Properties prop = new Properties();
            prop.load(inputStream);
            URI dbUri = null;
            try {
                dbUri = new URI(prop.getProperty("url"));
            } catch (URISyntaxException e1) {
                LOGGER.error(e1);
            }
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?ssl=true&requireSSL=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
//            dataSource.setUrl(prop.getProperty("DATABASE_URL") + "?requireSSL=true");
            dataSource.setUsername(dbUri.getUserInfo().split(":")[0]);
            dataSource.setPassword(dbUri.getUserInfo().split(":")[1]);
            dataSource.setInitialSize(10);
            dataSource.setMaxTotal(70);
            dataSource.setMaxIdle(30);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return dataSource;
    }

    @Bean
    @Profile("heroku")
    public DataSource herokuDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e1) {
            LOGGER.error(e1);
        }
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath());
        dataSource.setUsername(dbUri.getUserInfo().split(":")[0]);
        dataSource.setPassword(dbUri.getUserInfo().split(":")[1]);
        dataSource.setInitialSize(10);
        dataSource.setMaxTotal(70);
        dataSource.setMaxIdle(30);
        return dataSource;
    }


    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("com.postman");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties());
        return localSessionFactoryBean;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                setProperty("show_sql", "true");
                setProperty("hibernate.format_sql", "true");
                setProperty("hibernate.use_sql_comments", "true");
                setProperty("hibernate.hbm2ddl.auto", "update");
                setProperty("hibernate.cache.use_second_level_cache", "true");
                setProperty("hibernate.cache.use_query_cache", "true");
                setProperty("net.sf.ehcache.configurationResourceName", "/myehcache.xml");
                setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
            }
        };
    }

}
