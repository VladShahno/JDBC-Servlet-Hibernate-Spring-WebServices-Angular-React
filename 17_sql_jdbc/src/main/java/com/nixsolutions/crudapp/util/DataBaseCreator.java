package com.nixsolutions.crudapp.util;

import com.nixsolutions.crudapp.util.DataSourceUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCreator {

    private static final String ROLE_TABLE_SQL =
            "CREATE TABLE ROLE (" + " ROLE_ID IDENTITY NOT NULL,\n"
                    + "ROLE_NAME varchar(45) NOT NULL\n" + ");";


    private static final String USER_TABLE_SQL =
            "CREATE TABLE USER (" + " user_id IDENTITY NOT NULL,\n"
                    + "login varchar(20) NOT NULL,\n"
                    + "password varchar(20) NOT NULL,\n"
                    + "email varchar(30) NOT NULL,\n"
                    + "FIRST_NAME varchar(45) NOT NULL,\n"
                    + "LAST_NAME varchar(45) NOT NULL,\n"
                    + "birthday Date,\n"
                    + "role_id bigint NOT NULL,\n"
                    + "FOREIGN KEY(ROLE_ID) REFERENCES ROLE(ROLE_ID) ON DELETE CASCADE\n"
                    + "ON UPDATE CASCADE" + ");";

    Connection connection = null;
    Statement statement = null;

    public void createTableSql() throws SQLException {

        try {
            DataSource dataSource = DataSourceUtil.getDataSource();
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.execute(ROLE_TABLE_SQL);
            statement.execute(USER_TABLE_SQL);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}