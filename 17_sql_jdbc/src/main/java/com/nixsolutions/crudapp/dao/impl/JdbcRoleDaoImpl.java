package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.db.ConnectionInitializer;
import com.nixsolutions.crudapp.entity.Role;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcRoleDaoImpl implements RoleDao {

    private static final String INSERT_ROLE_SQL =
            "INSERT INTO ROLES" + "(name) VALUES " + " (?);";

    private static final String SELECT_ROLE_BY_ID = "SELECT id, name FROM ROLES where id =?";

    private static final String SELECT_ROLE_BY_NAME = "SELECT id, name FROM ROLES where name =?";

    private static final String UPDATE_ROLE = "UPDATE ROLES set name =? where id =?;";

    private static final String SELECT_ALL_USERS = "SELECT * FROM ROLES";

    private static final String REMOVE_ROLE_SQL = "DELETE FROM ROLES where id = ?;";

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
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Role> findAll() {

        List<Role> roles = new ArrayList<>();
        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                roles.add(new Role(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return role;
    }
}
