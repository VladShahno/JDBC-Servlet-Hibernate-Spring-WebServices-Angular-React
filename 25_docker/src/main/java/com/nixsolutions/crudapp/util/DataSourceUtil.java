package com.nixsolutions.crudapp.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            DataSourceUtil.class);

    private static BasicDataSource basicDataSource;

    static {
        try {
            if (basicDataSource == null) {
                LOGGER.info("Creating of new dataSource!");
                BasicDataSource ds = new BasicDataSource();

                Properties properties = new Properties();
                InputStream inputStream = DataSourceUtil.class.getClassLoader()
                        .getResourceAsStream("app.properties");
                if (inputStream == null) {
                    LOGGER.error("File not found!");
                    throw new IOException("File not found");
                }
                properties.load(inputStream);
                ds.setDriverClassName(properties.getProperty("driver"));
                ds.setUrl(properties.getProperty("url"));
                ds.setUsername(properties.getProperty("user"));
                ds.setMaxTotal(Integer.parseInt(
                        properties.getProperty("MaxPoolSize")));
                basicDataSource = ds;
            }
        } catch (IOException e) {
            LOGGER.error(
                    "Exception when getting data from file or create connection");
        }
    }

    public static Connection getConnection() {
        try {
            return basicDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish the connection to DB",
                    e);
        }
    }
}


