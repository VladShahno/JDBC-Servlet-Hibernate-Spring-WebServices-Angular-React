package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.exception.DataProcessingException;
import com.nixsolutions.crudapp.entity.Role;

import com.nixsolutions.crudapp.util.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRoleDaoImpl implements RoleDao {

    private DataSource dataSource = DataSourceUtil.getDataSource();

    private static final Logger LOGGER = LoggerFactory.getLogger(
            JdbcRoleDaoImpl.class);

    private static final String INSERT_ROLE_SQL =
            "INSERT INTO ROLE" + "(role_name) VALUES " + " (?);";

    private static final String SELECT_ROLE_BY_NAME = "SELECT role_id, role_name FROM ROLE WHERE role_name =?;";

    private static final String UPDATE_ROLE = "UPDATE ROLE set role_name =? WHERE role_id =?;";

    private static final String REMOVE_ROLE_SQL = "DELETE FROM ROLE WHERE role_name =?;";

    public JdbcRoleDaoImpl() throws IOException {
    }

    @Override
    public void create(Role role) {

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_ROLE_SQL)) {
                preparedStatement.setString(1, role.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Cannot create role", e);
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DataProcessingException(e);
            }
            connection.commit();
            connection.setAutoCommit(true);
            LOGGER.trace("Role was created " + role);
        } catch (SQLException e) {
            LOGGER.error("Connection error!", e);
        }
    }

    @Override
    public void update(Role role) {

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_ROLE)) {
                preparedStatement.setString(1, role.getName());
                preparedStatement.setLong(2, role.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Cannot update role " + role, e);
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DataProcessingException(e);
            }
            connection.commit();
            connection.setAutoCommit(true);
            LOGGER.info("Role was updated " + role);
        } catch (SQLException e) {
            LOGGER.error("Connection error ", e);
        }
    }

    @Override
    public void remove(Role role) {

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    REMOVE_ROLE_SQL)) {
                preparedStatement.setString(1, role.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Cannot remove role " + role, e);
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DataProcessingException(e);
            }
            connection.commit();
            connection.setAutoCommit(true);
            LOGGER.info("Role was removed " + role);
        } catch (SQLException e) {
            LOGGER.error("Connection error ", e);
        }
    }

    @Override
    public Role findByName(String name) throws SQLException {

        ResultSet resultSet = null;
        try (Connection connection = dataSource.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(
                SELECT_ROLE_BY_NAME)) {
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            Role role = new Role();
            while (resultSet.next()) {
                role.setId(resultSet.getLong("ROLE_ID"));
                role.setName(resultSet.getString("ROLE_NAME"));
            }
            connection.commit();
            LOGGER.info("Role found " + role);
            return role;
        } catch (SQLException e) {
            LOGGER.error("Cannot find role by name " + name, e);
            throw new DataProcessingException(e);
        }
    }
}
