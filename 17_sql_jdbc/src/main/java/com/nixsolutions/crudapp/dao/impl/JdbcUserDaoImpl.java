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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcUserDaoImpl extends AbstractJdbcDao implements UserDao {

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

    @Override
    public void create(User user) {
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    INSERT_USERS_SQL)) {
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
                connection.rollback();
                throw new DataProcessingException(e);
            }
        } catch (DataProcessingException dataProcessingException) {
            throw dataProcessingException;
        } catch (SQLException e) {
            log.warn("Can't close connection!", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public void update(User user) {

        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    UPDATE_USER)) {
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
                log.info(user + " was updated!");
            } catch (SQLException e) {
                connection.rollback();
                throw new DataProcessingException(e);
            }
        } catch (DataProcessingException dataProcessingException) {
            throw dataProcessingException;
        } catch (SQLException e) {
            log.warn(user + " was not updated!", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public void remove(User user) {
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    REMOVE_USER_SQL)) {
                statement.setLong(1, user.getId());
                statement.executeUpdate();
                connection.commit();
                log.info(user + " was deleted!");
            } catch (SQLException e) {
                connection.rollback();
                throw new DataProcessingException(e);
            }
        } catch (DataProcessingException dataProcessingException) {
            throw dataProcessingException;
        } catch (SQLException e) {
            log.warn(user + " was not deleted!", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_USERS)) {
                resultSet = preparedStatement.executeQuery();
                User user;
                while (resultSet.next()) {
                    user = getUser(resultSet);
                    users.add(user);
                }
                connection.commit();
                log.info("All Users were found!");
                return users;
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
            log.warn("Can't find Users!", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public User findById(Long id) {

        ResultSet resultSet = null;
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    SELECT_USER_BY_ID)) {
                statement.setLong(1, id);
                resultSet = statement.executeQuery();
                User user = new User();
                while (resultSet.next()) {
                    user = getUser(resultSet);
                }
                connection.commit();
                log.info(id + " was found!");
                return user;
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
            log.warn("Can't find login", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public User findByLogin(String login) {
        ResultSet resultSet = null;
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    SELECT_USER_BY_LOGIN)) {
                statement.setString(1, login);
                resultSet = statement.executeQuery();
                User user = new User();
                while (resultSet.next()) {
                    user = getUser(resultSet);
                }
                connection.commit();
                log.info(login + " was found!");
                return user;
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
            log.warn("Can't find login", e);
            throw new DataProcessingException(e);
        }
    }

    @Override
    public User findByEmail(String email) {
        ResultSet resultSet = null;
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    SELECT_USER_BY_EMAIL)) {
                statement.setString(1, email);
                resultSet = statement.executeQuery();
                User user = new User();
                while (resultSet.next()) {
                    user = getUser(resultSet);
                }
                log.info(email + " was found!");
                connection.commit();
                return user;
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
            log.warn("Can't find email", e);
            throw new DataProcessingException(e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setBirthday(resultSet.getDate("birthday"));
        user.setRoleId(resultSet.getLong("role_id"));
        return user;
    }
}
