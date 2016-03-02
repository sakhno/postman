package com.postman.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
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
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        try {
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME));
            dataSource.setDriverClassName(prop.getProperty("driver"));
            dataSource.setUrl(prop.getProperty("url"));
            dataSource.setUsername(prop.getProperty("user"));
            dataSource.setPassword(prop.getProperty("password"));
            dataSource.setInitialSize(20);
            dataSource.setMaxTotal(60);
            dataSource.setMaxIdle(30);
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (NullPointerException e) {
            LOGGER.warn("property file not found, trying to connect by getting system variable", e);
            final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
            dsLookup.setResourceRef(true);
            return dsLookup.getDataSource("java:/comp/env/jdbc/default");
        }
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
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

    Properties hibernateProperties(){
        return new Properties(){
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
