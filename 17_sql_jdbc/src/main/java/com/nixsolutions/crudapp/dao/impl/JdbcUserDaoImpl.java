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
import java.util.List;

public class JdbcUserDaoImpl implements UserDao {

    private static final String INSERT_USERS_SQL = "INSERT INTO USERS"
            + "  (login, password, email, firstName, lastName, birthday, role_id) VALUES "
            + " (?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_USER_BY_EMAIL = "SELECT id, login, password, email, firstName, lastName, birthday, role_id FROM USERS WHERE email =?";

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
    public void update(User user) {

    }

    @Override
    public void remove(User user) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User findByLogin(String login) {
        return null;
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

            System.out.println("fd");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                email = resultSet.getString("email");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Date birthday = resultSet.getDate("birthday");
                Long role_id = resultSet.getLong("role_id");

                user = new User(id, login, password, email, firstName, lastName,
                        null, role_id);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
