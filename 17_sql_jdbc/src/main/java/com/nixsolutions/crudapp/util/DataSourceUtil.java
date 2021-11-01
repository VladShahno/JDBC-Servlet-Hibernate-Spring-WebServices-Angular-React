package com.nixsolutions.crudapp.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            DataSourceUtil.class);
    private static BasicDataSource basicDataSource;

    public static DataSource getDataSource() throws IOException {
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
            basicDataSource = ds;
        }
        return basicDataSource;
    }
}


