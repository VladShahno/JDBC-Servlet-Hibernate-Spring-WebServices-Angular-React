package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.exception.DataProcessingException;
import com.nixsolutions.crudapp.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class JdbcUserDaoImpl extends AbstractJdbcDao implements UserDao {

    private Logger LOGGER = LoggerFactory.getLogger(JdbcRoleDaoImpl.class);

    private static final String INSERT_USERS_SQL = "INSERT INTO USER"
            + "  (login, password, email, first_name, last_name, birthday, role_id) VALUES "
            + " (?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_USER_BY_EMAIL = "SELECT user_id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE email =?;";

    private static final String SELECT_USER_BY_LOGIN = "SELECT user_id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE login =?;";

    private static final String SELECT_USER_BY_ID = "SELECT user_id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE user_id =?;";

    private static final String SELECT_ALL_USERS = "SELECT * FROM USER;";

    private static final String UPDATE_USER = "UPDATE USER set login=?, password=?, email=?, first_name=?, last_name=?, birthday=?, role_id=? WHERE user_id =?;";

    private static final String REMOVE_USER_SQL = "DELETE FROM USER WHERE user_id =?;";

    @Override
    public void create(User user) {

        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_USERS_SQL);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setDate(6,
                    new java.sql.Date(user.getBirthday().getTime()));
            preparedStatement.setLong(7, user.getRoleId());
            executePreparedStatementUpdate(connection, preparedStatement);
        } catch (SQLException e) {
            LOGGER.error("Cannot create user!", e);
        }
        LOGGER.info(user + " was created!");
    }

    @Override
    public void update(User user) {

        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setDate(6,
                    new Date(user.getBirthday().getTime()));
            preparedStatement.setLong(7, user.getRoleId());
            preparedStatement.setLong(8, user.getId());
            executePreparedStatementUpdate(connection, preparedStatement);
        } catch (SQLException e) {
            LOGGER.error("Cannot update!", e);
        }
        LOGGER.info("user was updated");
    }

    @Override
    public void remove(User user) {

        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    REMOVE_USER_SQL);
            preparedStatement.setLong(1, user.getId());
            executePreparedStatementUpdate(connection, preparedStatement);
        } catch (SQLException e) {
            LOGGER.error("Cannot delete user", e);
        }
        LOGGER.info("user was deleted");
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                SELECT_ALL_USERS)) {
            resultSet = preparedStatement.executeQuery();
            User user;
            while (resultSet.next()) {
                user = getUser(resultSet);
                users.add(user);
            }
            LOGGER.info("All users found");
            return users;
        } catch (SQLException e) {
            LOGGER.error("Cannot find users", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public User findById(Long id) {

        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user = getUser(resultSet);
            }
            LOGGER.info("User with id " + id + " found");
            return user;
        } catch (SQLException e) {
            LOGGER.error("Cannot find user with id " + id, e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public User findByLogin(String login) {

        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user = getUser(resultSet);
            }
            connection.commit();
            LOGGER.info("User with login " + login + " found");
            return user;
        } catch (SQLException e) {
            LOGGER.error("Cannot find user with login " + login, e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public User findByEmail(String email) {

        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user = getUser(resultSet);
            }
            LOGGER.info("User with email " + email + " found");
            connection.commit();
            return user;
        } catch (SQLException e) {
            LOGGER.error("Cannot find user with email " + email, e);
            throw new DataProcessingException(e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setId(resultSet.getLong("USER_ID"));
        user.setLogin(resultSet.getString("LOGIN"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setFirstName(resultSet.getString("FIRST_NAME"));
        user.setLastName(resultSet.getString("LAST_NAME"));
        user.setBirthday(resultSet.getDate("BIRTHDAY"));
        user.setRoleId(resultSet.getLong("ROLE_ID"));
        return user;
    }
}
