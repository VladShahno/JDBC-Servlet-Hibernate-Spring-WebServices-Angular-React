package com.nixsolutions.crudapp.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = { "com.nixsolutions.crudapp.service.impl",
        "com.nixsolutions.crudapp.dao.impl",
        "com.nixsolutions.crudapp.security", "com.nixsolutions.crudapp.util", })
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class AppConfig {

    private final Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(
                env.getProperty("hibernate.connection.driver_class"));
        dataSource.setUrl(env.getProperty("hibernate.connection.url"));
        dataSource.setUsername(
                env.getProperty("hibernate.connection.username"));
        dataSource.setPassword(
                env.getProperty("hibernate.connection.password"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(getDataSource());
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.setPackagesToScan(
                "com.nixsolutions.crudapp.entity");
        return localSessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}