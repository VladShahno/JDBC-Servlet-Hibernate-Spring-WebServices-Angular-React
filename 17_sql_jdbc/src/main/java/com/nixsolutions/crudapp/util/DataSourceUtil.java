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

// Сколько инстансов данного класса мы хотим держать в нашем приложении?
public class DataSourceUtil {

    // Обычно логгерам дают имена в некой иэрархической структуре. В какой? Почему у тебя имя логгера 'log'?
    //  Нужно будет отписать ответ в телегу
    private static final Logger log = LoggerFactory.getLogger("log");

    // Этот файл лежит в твоем класспазе. Тебе не нужно указывать его так.
    // Самое главное проблема тут - невозможность переиспользовать этот код в тестах
    private static String propertyFile = "src/main/resources/app.properties";
    private BasicDataSource dataSource = new BasicDataSource();


    public DataSourceUtil() {
        Properties properties = loadProperties();
        this.dataSource.setUrl(properties.getProperty("url"));
        this.dataSource.setUsername(properties.getProperty("user"));
        log.info("url and user were received!");
        // Если база будет не H2 придется менять этот код. Разве нет возможности получить драйвер с проперти фалйа!?
        dataSource.setDriver(Driver.load());
        log.info("driver was received!");
    }

    // Почему этот метод статический? Зачем он вообще существует? Если ты хотел менять в рантайме то где логика?
    // То что ты вызываещь этот метод перед конструктором - просто мрак
    public static void setPropertyFile(String propertyFile) {
        DataSourceUtil.propertyFile = propertyFile;
        log.info("propertyFile was set!");
    }

    // Все настройки для базы данных должны храниться в property файле и загружаться как ресурс.
    // Где загрузка как ресурс?
    private Properties loadProperties() {
        Properties prop = new Properties();
        try (InputStream in = new FileInputStream(propertyFile)) {
            prop.load(in);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Почему бы не сделать его статическим?
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

