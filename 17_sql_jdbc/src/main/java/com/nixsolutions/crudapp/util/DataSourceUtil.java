package com.nixsolutions.crudapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.h2.Driver;


import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceUtil {

    private static final Logger log = LoggerFactory.getLogger("log");

    private static String propertyFile = "src/main/resources/app.properties";
    private BasicDataSource dataSource = new BasicDataSource();

    public DataSourceUtil() {
        Properties properties = loadProperties();
        this.dataSource.setUrl(properties.getProperty("url"));
        this.dataSource.setUsername(properties.getProperty("user"));
        log.info("url and user were received!");
        dataSource.setDriver(Driver.load());
        log.info("driver was received!");
    }

    public static void setPropertyFile(String propertyFile) {
        DataSourceUtil.propertyFile = propertyFile;
        log.info("propertyFile was set!");
    }

    private Properties loadProperties() {
        Properties prop = new Properties();
        try (InputStream in = new FileInputStream(propertyFile)) {
            prop.load(in);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            log.info("Try to get connection");
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("can't get connection", e);
            throw new RuntimeException(e);
        }
    }
}

