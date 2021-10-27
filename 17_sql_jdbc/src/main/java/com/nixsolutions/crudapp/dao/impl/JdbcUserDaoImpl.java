package com.nixsolutions.crudapp.dao.impl;

import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.db.ConnectionInitializer;
import com.nixsolutions.crudapp.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcUserDaoImpl implements UserDao {

    private static final Logger log = LoggerFactory.getLogger("log");

    private static final String INSERT_USERS_SQL = "INSERT INTO USERS"
            + "  (login, password, email, firstName, lastName, birthday, role_id) VALUES "
            + " (?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_USER_BY_EMAIL = "SELECT id, login, password, email, firstName, lastName, birthday, role_id FROM USERS WHERE email =?;";

    private static final String SELECT_USER_BY_LOGIN = "SELECT id, login, password, email, firstName, lastName, birthday, role_id FROM USERS WHERE login =?;";

    private static final String SELECT_USER_BY_ID = "SELECT id, login, password, email, firstName, lastName, birthday, role_id FROM USERS WHERE id =?;";

    private static final String SELECT_ALL_USERS = "SELECT * FROM USERS;";

    private static final String UPDATE_USER = "UPDATE USERS set login=?, password=?, email=?, firstName=?, lastName=?, birthday=?, role_id=? WHERE id =?;";

    private static final String REMOVE_USER_SQL = "DELETE FROM USERS WHERE id =?;";

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    @Override
    public void create(User user) throws SQLException {
        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setDate(6, (Date) user.getBirthday());
            preparedStatement.setLong(7, user.getRoleId());

            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();

            log.info(user + " was created!");
        } catch (SQLException e) {
            log.warn("can't create User", e);
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
    public void update(User user) throws SQLException {

        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE_USER);

            preparedStatement.setLong(8, user.getId());
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setDate(6, user.getBirthday());
            preparedStatement.setLong(7, user.getRoleId());

            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();

            log.info(user + " was updated!");
        } catch (SQLException e) {
            log.warn(user + " was not updated!", e);
            connection.rollback();
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
    public void remove(User user) throws SQLException {

        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(REMOVE_USER_SQL);

            preparedStatement.setLong(1, user.getId());

            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();

            log.info(user + " was deleted!");
        } catch (SQLException e) {
            log.warn(user + " was not deleted!", e);
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
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("birthday");
                Date birthday = resultSet.getDate("birthday");
                Long roleId = resultSet.getLong("role_id");
                users.add(new User(id, login, password, email, firstName,
                        lastName, birthday, roleId));
                log.info("All Users were found!");
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            log.warn("Can't find Users!", e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
        return users;
    }

    @Override
    public User findById(Long id) {

        User user = null;
        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("birthday");
                Date birthday = resultSet.getDate("birthday");
                Long roleId = resultSet.getLong("role_id");

                user = new User(id, login, password, email, firstName, lastName,
                        birthday, roleId);
                log.info(id + " was found!");
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            log.warn("Can't find id", e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
        return user;
    }

    @Override
    public User findByLogin(String login) {

        User user = null;
        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    SELECT_USER_BY_LOGIN);

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("birthday");
                Date birthday = resultSet.getDate("birthday");
                Long roleId = resultSet.getLong("role_id");

                user = new User(id, login, password, email, firstName, lastName,
                        birthday, roleId);
                log.info(login + " was found!");
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            log.warn("Can't find login", e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {

        User user = null;
        try {
            DataSource dataSource = ConnectionInitializer.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    SELECT_USER_BY_EMAIL);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                email = resultSet.getString("email");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("birthday");
                Date birthday = resultSet.getDate("birthday");
                Long roleId = resultSet.getLong("role_id");

                user = new User(id, login, password, email, firstName, lastName,
                        birthday, roleId);
                log.info(email + " was found!");
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            log.warn("Can't find email", e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                log.warn("Can't close connection!", e);
            }
        }
        return user;
    }
}
