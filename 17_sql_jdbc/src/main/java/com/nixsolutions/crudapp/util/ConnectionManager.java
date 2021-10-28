package com.nixsolutions.crudapp.util;

import com.nixsolutions.crudapp.exception.DataProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionManager {

    private static final Logger log = LoggerFactory.getLogger("log");

    public void rollback(Connection connection) {
        try {
            connection.rollback();
            log.info("rollback was successful");
        } catch (SQLException ex) {
            log.error("Cannot rollback transaction ", ex);
            throw new DataProcessingException("Cannot rollback connection", ex);
        }
    }

    public void finish(PreparedStatement statement, Connection connection,
            ResultSet resultSet) {
        closeStatement(statement);
        closeConnection(connection);
        closeResultSet(resultSet);
    }

    public void closeResultSet(ResultSet resultSet) {
        try {
            log.info("Closing resultset");
            if (resultSet != null) {
                resultSet.close();
            }
            log.trace("Resultset closed");
        } catch (SQLException e) {
            log.error("Cannot close resultSet", e);
            throw new DataProcessingException(e);
        }
    }

    public void closeConnection(Connection connection) {
        try {
            log.trace("Closing connection");
            if (connection != null) {
                connection.close();
            }
            log.trace("Connection closed");
        } catch (SQLException e) {
            log.error("Cannot close connection", e);
            throw new DataProcessingException(e);
        }
    }

    public void closeStatement(PreparedStatement statement) {
        try {
            log.trace("Closing statement");
            if (statement != null) {
                statement.close();
            }
            log.trace("PreparedStatement closed");
        } catch (SQLException e) {
            log.error("Cannot close statement", e);
            throw new DataProcessingException(e);
        }
    }
}
