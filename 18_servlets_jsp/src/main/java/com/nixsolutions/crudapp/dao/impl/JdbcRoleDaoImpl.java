package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.exception.DataProcessingException;
import com.nixsolutions.crudapp.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRoleDaoImpl extends AbstractJdbcDao implements RoleDao {

    private Logger LOGGER = LoggerFactory.getLogger(JdbcRoleDaoImpl.class);

    private static final String INSERT_ROLE_SQL =
            "INSERT INTO ROLE" + "(role_name) VALUES " + " (?);";

    private static final String SELECT_ROLE_BY_NAME = "SELECT role_id, role_name FROM ROLE WHERE role_name =?;";

    private static final String UPDATE_ROLE = "UPDATE ROLE set role_name =? WHERE role_id =?;";

    private static final String REMOVE_ROLE_SQL = "DELETE FROM ROLE WHERE role_id =?;";

    private static final String SELECT_ALL_ROLES = "SELECT * FROM ROLE;";

    private static final String SELECT_ROLE_BY_ID = "SELECT role_id, role_name FROM ROLE WHERE role_id =?";

    @Override
    public void create(Role role) {

        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_ROLE_SQL);
            preparedStatement.setString(1, role.getName());
            executePreparedStatementUpdate(connection, preparedStatement);
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
        LOGGER.info("Role was created " + role);
    }

    @Override
    public void update(Role role) {

        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_ROLE);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setLong(2, role.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidationException(e);
        }
        LOGGER.info("Role was updated " + role);
    }

    @Override
    public void remove(Role role) {

        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    REMOVE_ROLE_SQL);
            preparedStatement.setLong(1, role.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot remove role " + role, e);
        }
        LOGGER.info("Role was removed " + role);
    }

    @Override
    public Role findByName(String name) {

        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
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

    @Override
    public List<Role> findAll() {

        List<Role> roles = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                SELECT_ALL_ROLES)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("role_id");
                String name = resultSet.getString("role_name");
                roles.add(new Role(id, name));
            }
            LOGGER.info("All roles found");
            return roles;
        } catch (SQLException e) {
            LOGGER.error("Cannot find roles", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public Role findById(Long id) {

        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                SELECT_ROLE_BY_ID)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Role role = new Role();
            if (resultSet.next()) {
                String name = resultSet.getString("role_name");
                role = new Role(id, name);
            }
            LOGGER.info("Role with id " + id + " found");
            return role;
        } catch (SQLException e) {
            LOGGER.error("Cannot find role with id " + id, e);
            throw new DataProcessingException(e);
        }
    }
}
