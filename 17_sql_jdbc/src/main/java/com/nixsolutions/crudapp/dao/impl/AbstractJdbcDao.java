package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.exception.DataProcessingException;
import com.nixsolutions.crudapp.util.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractJdbcDao {

    private Logger LOGGER = LoggerFactory.getLogger(AbstractJdbcDao.class);

    protected Connection getConnection() {
        return DataSourceUtil.getConnection();
    }

    protected void executePreparedStatementUpdate(Connection connection,
            PreparedStatement preparedStatement) throws SQLException {
        try (connection; preparedStatement) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Cannot executeUpdate.", e);
            connection.rollback();
            connection.setAutoCommit(true);
            throw new DataProcessingException(e);
        }
    }
}
