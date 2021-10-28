package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.AbstractJdbcDao;
import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.exception.DataProcessingException;
import com.nixsolutions.crudapp.util.ConnectionManager;
import com.nixsolutions.crudapp.util.DataSourceUtil;
import com.nixsolutions.crudapp.entity.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcRoleDaoImpl extends AbstractJdbcDao implements RoleDao {

    private ConnectionManager connectionManager;

    private static final Logger log = LoggerFactory.getLogger("log");

    private static final String INSERT_ROLE_SQL =
            "INSERT INTO ROLES" + "(role_name) VALUES " + " (?);";

    private static final String SELECT_ROLE_BY_NAME = "SELECT role_id, role_name FROM ROLES WHERE role_name =?;";

    private static final String UPDATE_ROLE = "UPDATE ROLES set role_name =? WHERE role_id =?;";

    private static final String REMOVE_ROLE_SQL = "DELETE FROM ROLES WHERE role_id = ?;";

    public JdbcRoleDaoImpl() {
        connectionManager = new ConnectionManager();
    }

    @Override
    public void create(Role role) {

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_ROLE_SQL);
            statement.setString(1, role.getName());
            statement.executeUpdate();
            connection.commit();
            log.trace("Role was created " + role);
        } catch (SQLException e) {
            log.error("Cannot create role", e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, null);
        }
    }

    @Override
    public void update(Role role) {

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_ROLE);
            statement.setString(1, role.getName());
            statement.setLong(2, role.getId());
            statement.executeUpdate();
            connection.commit();
            log.info("Role was updated " + role);
        } catch (SQLException e) {
            log.error("Cannot update role " + role, e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, null);
        }
    }

    @Override
    public void remove(Role role) {

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(REMOVE_ROLE_SQL);
            statement.setString(1, role.getName());
            statement.executeUpdate();
            connection.commit();
            log.info("Role was removed " + role);
        } catch (SQLException e) {
            log.error("Cannot remove role " + role, e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, null);
        }
    }

    @Override
    public Role findByName(String name) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SELECT_ROLE_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            Role role = new Role();
            while (resultSet.next()) {
                role.setId(resultSet.getLong("role_id"));
                role.setName(resultSet.getString("role_name"));
            }
            connection.commit();
            log.info("Role found " + role);
            return role;
        } catch (SQLException e) {
            log.error("Cannot find role by name " + name, e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, resultSet);
        }
    }
}
