package com.nixsolutions.crudapp.util;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCreator {

    private static final String ROLE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS ROLE (" + " ROLE_ID IDENTITY NOT NULL,\n"
                    + "ROLE_NAME varchar(45) NOT NULL\n" + ");";

    private static final String USER_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS USER (" + " user_id IDENTITY NOT NULL,\n"
                    + "login varchar(20) NOT NULL,\n"
                    + "password varchar(20) NOT NULL,\n"
                    + "email varchar(30) NOT NULL,\n"
                    + "FIRST_NAME varchar(45) NOT NULL,\n"
                    + "LAST_NAME varchar(45) NOT NULL,\n" + "birthday Date,\n"
                    + "role_id bigint NOT NULL,\n"
                    + "FOREIGN KEY(ROLE_ID) REFERENCES ROLE(ROLE_ID) ON DELETE CASCADE\n"
                    + "ON UPDATE CASCADE" + ");";

    public static void createTableSql() throws IOException {
        DataSource dataSource = DataSourceUtil.getDataSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(ROLE_TABLE_SQL);
            statement.execute(USER_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}