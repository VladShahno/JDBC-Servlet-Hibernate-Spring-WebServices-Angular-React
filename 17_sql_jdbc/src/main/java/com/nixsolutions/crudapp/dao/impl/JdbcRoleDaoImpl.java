package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.AbstractJdbcDao;
import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.exception.DataProcessingException;
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

    private static final Logger log = LoggerFactory.getLogger("log");

    private static final String INSERT_ROLE_SQL =
            "INSERT INTO ROLES" + "(name) VALUES " + " (?);";

    private static final String SELECT_ROLE_BY_NAME = "SELECT id, name FROM ROLES WHERE name =?;";

    private static final String UPDATE_ROLE = "UPDATE ROLES set name =? WHERE id =?;";

    private static final String REMOVE_ROLE_SQL = "DELETE FROM ROLES WHERE id = ?;";

    @Override
    public void create(Role role) {
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    INSERT_ROLE_SQL)) {
                statement.setString(1, role.getName());
                statement.executeUpdate();
                connection.commit();
                log.info(role + " was created!");
            } catch (SQLException e) {
                connection.rollback();
                throw new DataProcessingException(e);
            }
        } catch (SQLException e) {
            log.warn("Role not created!", e);
            throw new DataProcessingException(e);
        } catch (DataProcessingException dataProcessingException) {
            throw dataProcessingException;
        }
    }

    @Override
    public void update(Role role) {
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    UPDATE_ROLE)) {
                statement.setString(1, role.getName());
                statement.setLong(2, role.getId());
                statement.executeUpdate();
                connection.commit();
                log.info(role + " was updated!");
            } catch (SQLException e) {
                connection.rollback();
                throw new DataProcessingException(e);
            }
        } catch (DataProcessingException dataProcessingException) {
            throw dataProcessingException;
        } catch (SQLException e) {
            log.warn(role + " was not updated!", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public void remove(Role role) {
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    REMOVE_ROLE_SQL)) {
                statement.setString(1, role.getName());
                statement.executeUpdate();
                connection.commit();
                log.info(role + " was deleted!");
            } catch (SQLException e) {
                connection.rollback();
                throw new DataProcessingException(e);
            }
        } catch (DataProcessingException dataProcessingException) {
            throw dataProcessingException;
        } catch (SQLException e) {
            log.warn(role + " was not deleted!", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public Role findByName(String name) {
        ResultSet resultSet = null;
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    SELECT_ROLE_BY_NAME)) {
                statement.setString(1, name);
                Role role = new Role();
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    role.setId(resultSet.getLong("id"));
                    role.setName(resultSet.getString("name"));
                }
                connection.commit();
                log.info(role + " was found!");
                return role;
            } catch (SQLException e) {
                connection.rollback();
                throw new DataProcessingException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }
        } catch (DataProcessingException dataProcessingException) {
            throw dataProcessingException;
        } catch (SQLException e) {
            log.warn(name + " was not found!", e);
            throw new DataProcessingException(e);
        }
    }
}
