package com.postman.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
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

//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        try {
//            Properties prop = new Properties();
//            prop.load(getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));
//            dataSource.setDriverClassName(prop.getProperty("driver"));
//            dataSource.setUrl(prop.getProperty("url"));
//            dataSource.setUsername(prop.getProperty("user"));
//            dataSource.setPassword(prop.getProperty("password"));
//            dataSource.setInitialSize(10);
//            dataSource.setMaxTotal(70);
//            dataSource.setMaxIdle(30);
//        } catch (IOException e) {
//            LOGGER.error(e);
//        } catch (NullPointerException e) {
//            LOGGER.warn("property file not found, trying to connect by getting system variable");
//            URI dbUri = null;
//            try {
//                dbUri = new URI(System.getenv("DATABASE_URL"));
//            } catch (URISyntaxException e1) {
//                LOGGER.error(e1);
//            }
//            dataSource.setDriverClassName("org.postgresql.Driver");
//            dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath());
//            dataSource.setUsername(dbUri.getUserInfo().split(":")[0]);
//            dataSource.setPassword(dbUri.getUserInfo().split(":")[1]);
//            dataSource.setInitialSize(10);
//            dataSource.setMaxTotal(70);
//            dataSource.setMaxIdle(30);
//        }
//        return dataSource;
//    }

    @Bean
    @Profile("default")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        try {
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));
            dataSource.setDriverClassName(prop.getProperty("driver"));
            dataSource.setUrl(prop.getProperty("url"));
            dataSource.setUsername(prop.getProperty("user"));
            dataSource.setPassword(prop.getProperty("password"));
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
    @Profile("default")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setPackagesToScan("com.postman");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties());
        return localSessionFactoryBean;
    }

    @Bean
    @Profile("heroku")
    public LocalSessionFactoryBean herokuSessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(herokuDataSource());
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
            }
        };
    }

}
