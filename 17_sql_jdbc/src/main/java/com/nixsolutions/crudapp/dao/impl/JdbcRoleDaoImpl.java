package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.db.ConnectionInitializer;
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

public class JdbcRoleDaoImpl implements RoleDao {

    private static final Logger log = LoggerFactory.getLogger("log");

    private static final String INSERT_ROLE_SQL =
            "INSERT INTO ROLES" + "(name) VALUES " + " (?);";

    private static final String SELECT_ROLE_BY_ID = "SELECT id, name FROM ROLES WHERE id =?;";

    private static final String SELECT_ROLE_BY_NAME = "SELECT id, name FROM ROLES WHERE name =?;";

    private static final String UPDATE_ROLE = "UPDATE ROLES set name =? WHERE id =?;";

    private static final String SELECT_ALL_ROLES = "SELECT * FROM ROLES;";

    private static final String REMOVE_ROLE_SQL = "DELETE FROM ROLES WHERE id = ?;";

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    @Override
    public void create(Role role) throws SQLException {

        try {

            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT_ROLE_SQL);

            preparedStatement.setString(1, role.getName());

            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();

            log.info(role + " was created!");
        } catch (SQLException e) {
            connection.rollback();
            log.warn("Role not created!", e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
    }

    @Override
    public void update(Role role) throws SQLException {

        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE_ROLE);

            preparedStatement.setString(1, role.getName());
            preparedStatement.setLong(2, role.getId());

            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();

            log.info(role + " was updated!");
        } catch (SQLException e) {
            log.warn(role + " was not updated!", e);
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
    }

    @Override
    public void remove(Role role) throws SQLException {

        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(REMOVE_ROLE_SQL);

            preparedStatement.setLong(1, role.getId());

            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();

            log.info(role + " was deleted!");
        } catch (SQLException e) {
            log.warn(role + " was not deleted!", e);
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
    }

    @Override
    public List<Role> findAll() {

        List<Role> roles = new ArrayList<>();
        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_ROLES);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                roles.add(new Role(id, name));

                log.info("All Roles were found!");
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            log.warn("Can't find Roles!", e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
        return roles;
    }

    @Override
    public Role findById(Long id) {

        Role role = null;
        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_ID);

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                role = new Role(id, name);

                log.info(role + " was found!");
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            log.warn(role + " was not found!", e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
        return role;
    }

    @Override
    public Role findByName(String name) {

        Role role = null;
        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    SELECT_ROLE_BY_NAME);

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                role = new Role(id, name);

                log.info(role + " was found!");
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            log.warn(role + " was not found!", e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
        return role;
    }
}
