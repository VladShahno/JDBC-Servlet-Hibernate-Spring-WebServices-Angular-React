package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.exception.ConnectionException;
import com.nixsolutions.crudapp.exception.DataProcessingException;
import com.nixsolutions.crudapp.util.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractJdbcDao {

    protected DataSource dataSource = DataSourceUtil.getDataSource();
    protected Logger logger = LoggerFactory.getLogger(JdbcUserDaoImpl.class);

    protected AbstractJdbcDao() throws IOException {
    }

    protected Connection getConnection() throws IOException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException(
                    "Can't establish the connection to DB", e);
        }
    }

    protected void executePreparedStatementUpdate(Connection connection,
            PreparedStatement preparedStatement) throws SQLException {
        try {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Cannot execute creation.", e);
            connection.rollback();
            connection.setAutoCommit(true);
            throw new DataProcessingException(e);
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }
}
