package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.AbstractJdbcDao;
import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.exception.DataProcessingException;
import com.nixsolutions.crudapp.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.nixsolutions.crudapp.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcUserDaoImpl extends AbstractJdbcDao implements UserDao {

    private ConnectionManager connectionManager;

    private static final Logger log = LoggerFactory.getLogger("log");

    private static final String INSERT_USERS_SQL = "INSERT INTO USER"
            + "  (login, password, email, first_name, last_name, birthday, role_id) VALUES "
            + " (?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_USER_BY_EMAIL = "SELECT user_id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE email =?;";

    private static final String SELECT_USER_BY_LOGIN = "SELECT user_id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE login =?;";

    private static final String SELECT_USER_BY_ID = "SELECT user_id, login, password, email, first_name, last_name, birthday, role_id FROM USER WHERE user_id =?;";

    private static final String SELECT_ALL_USERS = "SELECT * FROM USER;";

    private static final String UPDATE_USER = "UPDATE USER set login=?, password=?, email=?, first_name=?, last_name=?, birthday=?, role_id=? WHERE user_id =?;";

    private static final String REMOVE_USER_SQL = "DELETE FROM USER WHERE user_id =?;";

    public JdbcUserDaoImpl() {
        connectionManager = new ConnectionManager();
    }

    @Override
    public void create(User user) {

        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_USERS_SQL);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setDate(6,
                    new java.sql.Date(user.getBirthday().getTime()));
            statement.setLong(7, user.getRoleId());
            statement.executeUpdate();
            connection.commit();
            log.info(user + " was created!");
        } catch (SQLException e) {
            log.error("Cannot create user", e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, null);
        }
    }

    @Override
    public void update(User user) {

        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = createConnection();
            // Тут ты поставил autoCommit = false.
            // Получается по дефолту стоит true
            // А почему тогда ты обратно не поставил true?
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setDate(6,
                    new java.sql.Date(user.getBirthday().getTime()));
            statement.setLong(7, user.getRoleId());
            statement.setLong(8, user.getId());
            statement.executeUpdate();
            connection.commit();
            log.info("user was updated");
        } catch (SQLException e) {
            log.error("Cannot update user", e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, null);
        }
    }

    @Override
    public void remove(User user) {

        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(REMOVE_USER_SQL);
            statement.setLong(1, user.getId());
            statement.executeUpdate();
            connection.commit();
            log.info("user was deleted");
        } catch (SQLException e) {
            log.error("Cannot delete user", e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, null);
        }
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SELECT_ALL_USERS);
            resultSet = statement.executeQuery();
            User user;
            while (resultSet.next()) {
                user = getUser(resultSet);
                users.add(user);
            }
            connection.commit();
            log.info("All users found");
            return users;
        } catch (SQLException e) {
            log.error("Cannot get all users", e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, resultSet);
        }
    }

    @Override
    public User findById(Long id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user = getUser(resultSet);
            }
            connection.commit();
            log.info("User with id " + id + " found");
            return user;
        } catch (SQLException e) {
            log.error("Cannot find user with id " + id, e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, resultSet);
        }
    }

    @Override
    public User findByLogin(String login) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user = getUser(resultSet);
            }
            connection.commit();
            log.info("User with login " + login + " found");
            return user;
        } catch (SQLException e) {
            log.error("Cannot find user with login " + login, e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, resultSet);
        }
    }

    @Override
    public User findByEmail(String email) {
        // Зачем здесь вручную управлять транзакцией? Нужно использовать только там где в этом есть логика!
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = createConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user = getUser(resultSet);
            }
            log.info("User with email " + email + " found");
            connection.commit();
            return user;
        } catch (SQLException e) {
            log.error("Cannot find user with email " + email, e);
            connectionManager.rollback(connection);
            throw new DataProcessingException(e);
        } finally {
            connectionManager.finish(statement, connection, resultSet);
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
