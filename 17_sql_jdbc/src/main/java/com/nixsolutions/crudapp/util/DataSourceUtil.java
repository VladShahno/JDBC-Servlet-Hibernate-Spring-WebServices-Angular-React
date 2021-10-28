package com.nixsolutions.crudapp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSourceUtil {

    private static String propertyFile = "src/main/resources/app.properties";
    private BasicDataSource dataSource = new BasicDataSource();

    public DataSourceUtil() {
        Properties properties = loadProperties();
        this.dataSource.setUrl(properties.getProperty("url"));
        this.dataSource.setUsername(properties.getProperty("user"));
        this.dataSource.setDriverClassName("database.driver");
    }

    public static void setPropertyFile(String propertyFile) {
        DataSourceUtil.propertyFile = propertyFile;
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
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}

