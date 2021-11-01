package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.exception.DataProcessingException;
import com.nixsolutions.crudapp.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.nixsolutions.crudapp.util.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class JdbcUserDaoImpl implements UserDao {

    private DataSource dataSource = DataSourceUtil.getDataSource();

    private static final Logger LOGGER = LoggerFactory.getLogger(
            JdbcUserDaoImpl.class);

    private static final String INSERT_USERS_SQL = "INSERT INTO USER"
            + "  (login, password, email, first_name, last_name, birthday, role_id) VALUES "
            + " (?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_USER_BY_EMAIL = "SELECT user_id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE email =?;";

    private static final String SELECT_USER_BY_LOGIN = "SELECT user_id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE login =?;";

    private static final String SELECT_USER_BY_ID = "SELECT user_id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE user_id =?;";

    private static final String SELECT_ALL_USERS = "SELECT * FROM USER;";

    private static final String UPDATE_USER = "UPDATE USER set login=?, password=?, email=?, first_name=?, last_name=?, birthday=?, role_id=? WHERE user_id =?;";

    private static final String REMOVE_USER_SQL = "DELETE FROM USER WHERE user_id =?;";

    public JdbcUserDaoImpl() throws IOException {}

    @Override
    public void create(User user) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_USERS_SQL)) {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getFirstName());
                preparedStatement.setString(5, user.getLastName());
                preparedStatement.setDate(6,
                        new java.sql.Date(user.getBirthday().getTime()));
                preparedStatement.setLong(7, user.getRoleId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Cannot create user", e);
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DataProcessingException(e);
            }
            connection.commit();
            connection.setAutoCommit(true);
            LOGGER.info(user + " was created!");
        } catch (SQLException e) {
            LOGGER.error("Connection error!", e);
        }
    }

    @Override
    public void update(User user) {

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_USER)) {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getFirstName());
                preparedStatement.setString(5, user.getLastName());
                preparedStatement.setDate(6,
                        new Date(user.getBirthday().getTime()));
                preparedStatement.setLong(7, user.getRoleId());
                preparedStatement.setLong(8, user.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Cannot update user", e);
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DataProcessingException(e);
            }
            connection.commit();
            connection.setAutoCommit(true);
            LOGGER.info("user was updated");
        } catch (SQLException e) {
            LOGGER.error("Connection error!", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public void remove(User user) {

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    REMOVE_USER_SQL)) {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Cannot delete user", e);
                connection.rollback();
                connection.setAutoCommit(true);
                throw new DataProcessingException(e);
            }
            connection.commit();
            connection.setAutoCommit(true);
            LOGGER.info("user was deleted");
        } catch (SQLException e) {
            LOGGER.error("Connection error!", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
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
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
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
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
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
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
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
