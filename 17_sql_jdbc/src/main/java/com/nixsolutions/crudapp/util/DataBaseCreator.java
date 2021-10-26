package com.nixsolutions.crudapp.util;

import com.nixsolutions.crudapp.db.ConnectionInitializer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCreator {

    private static final String USER_TABLE_SQL =
            "CREATE TABLE USERS (" + " id IDENTITY NOT NULL,\n"
                    + "login varchar(20) NOT NULL,\n"
                    + "password varchar(20) NOT NULL,\n"
                    + "email varchar(30) NOT NULL,\n"
                    + "firstName varchar(45) NOT NULL,\n"
                    + "lastName varchar(45) NOT NULL,\n"
                    + "birthday Date,\n"
                    + "role_id bigint NOT NULL,\n"
                    + "FOREIGN KEY(role_id) REFERENCES ROLES(id)\n" + ");";

    private static final String ROLE_TABLE_SQL =
            "CREATE TABLE ROLES (" + " id IDENTITY NOT NULL,\n"
                    + "name varchar(45) NOT NULL\n" + ");";

    Connection connection = null;
    Statement statement = null;

    public void createTableSql() throws SQLException {

        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.execute(ROLE_TABLE_SQL);
            statement.execute(USER_TABLE_SQL);
        } catch (SQLException e) {
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