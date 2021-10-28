package com.nixsolutions.test.dao.impl;

import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.dao.impl.JdbcUserDaoImpl;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.test.dao.DbConfig;

import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static org.dbunit.Assertion.assertEqualsIgnoreCols;
import static org.junit.Assert.assertEquals;

public class UserDaoImplTest extends DbConfig {
    private static final String SAVE_USER_XML = "src/test/resources/dataset/user/save-user.xml";
    private static final String UPDATE_USER_XML = "src/test/resources/dataset/user/update-user.xml";
    private static final String REMOVE_USER_XML = "src/test/resources/dataset/user/remove-user.xml";
    private static final String FIND_BY_LOGIN_USER_XML = "src/test/resources/dataset/user/find-login-user.xml";
    private static final String FIND_BY_EMAIL_USER_XML = "src/test/resources/dataset/user/find-EMAIL-user.xml";
    private static final String FIND_ALL_USERS_XML = "src/test/resources/dataset/user/findall-user.xml";
    private static final String TABLE_USER = "USER";
    private static final String[] IGNORE_COLS = { "USER_ID" };
    private UserDao userDao;

    @BeforeClass
    public static void ddlOperations() throws Exception {
        new DbConfig().createTables();
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userDao = new JdbcUserDaoImpl();
    }

    @Test
    public void testCreateUser() throws Exception {
        userDao.create(
                new User("Log3", "Pass3", "e3@gmail.com", "Name3", "LastName3",
                        Date.valueOf("1999-09-09"), 1L));
        ITable expected = getExpectedTable(SAVE_USER_XML, TABLE_USER);
        ITable actual = getActualTable(TABLE_USER);
        assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User("admin", "54321", "ad@gmail.com", "Serhii",
                "Yevtushok", Date.valueOf("1993-07-28"), 1L);
        user.setId(1L);
        userDao.update(user);

        ITable expected = getExpectedTable(UPDATE_USER_XML, TABLE_USER);
        ITable actual = getActualTable(TABLE_USER);

        assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
    }

    @Test
    public void testRemoveUser() throws Exception {
        User user = new User();
        user.setId(2L);
        userDao.remove(user);

        ITable expected = getExpectedTable(REMOVE_USER_XML, TABLE_USER);
        ITable actual = getActualTable(TABLE_USER);
        assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
    }

    @Test
    public void findAll() throws Exception {
        List<User> actual = userDao.findAll();
        ITable expected = getExpectedTable(FIND_ALL_USERS_XML, TABLE_USER);
        assertEquals(actual.size(), expected.getRowCount());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i).getLogin(),
                    expected.getValue(i, "LOGIN"));
        }
    }

    @Test
    public void findByLogin() throws Exception {
        User actual = userDao.findByLogin("ad");
        ITable expected = getExpectedTable(FIND_BY_LOGIN_USER_XML, TABLE_USER);
        assertEquals("Row count must be 1", 1, expected.getRowCount());
        assertEquals(actual.getLogin(), expected.getValue(0, "LOGIN"));
    }

    @Test
    public void findByEmail() throws Exception {
        User actual = userDao.findByEmail("ad@gmail.com");
        ITable expected = getExpectedTable(FIND_BY_EMAIL_USER_XML, TABLE_USER);
        assertEquals("Row count must be 1", 1, expected.getRowCount());
        assertEquals(actual.getEmail(), expected.getValue(0, "EMAIL"));
    }
}