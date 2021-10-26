package com.nixsolutions.crudapp.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionInitializer {

    private static BasicDataSource dataSource;

    static {
        try {
            dataSource = new BasicDataSource();
            Properties properties = new Properties();

            InputStream inputStream = ConnectionInitializer.class.getClassLoader()
                    .getResourceAsStream("jdbc.properties");
            if (inputStream == null) {
                throw new IOException("File not found");
            }
            properties.load(inputStream);
            dataSource.setDriverClassName(
                    properties.getProperty("database.driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("user"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}

