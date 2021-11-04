package com.nixsolutions.test.dao.impl;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.nixsolutions.crudapp.dao.impl.AbstractJdbcDao;
import com.nixsolutions.crudapp.dao.impl.JdbcUserDaoImpl;
import com.nixsolutions.crudapp.entity.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class UserDaoImplTest extends AbstractJdbcDao {

    JdbcUserDaoImpl jdbcUserDao = new JdbcUserDaoImpl();

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(getConnection());

    public UserDaoImplTest() throws IOException {
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.INSERT, cleanBefore = true, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldReturnTheUsernameOfTheUserAfterAddingItToTheDatabase()
            throws SQLException, IOException {
        User user = getNewUser();
        jdbcUserDao.create(user);
        List<User> list = jdbcUserDao.findAll();
        assertEquals(1, list.size());
    }

    @Test
    @DataSet(value = { "dataset/dataRole.xml",
            "dataset/dataUser.xml" }, strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, cleanBefore = true, tableOrdering = {
            "role", "user" }, executeScriptsBefore = { "sql/DROP.sql",
            "sql/DDL.sql" })
    public void shouldReturnAllUsersFromDatabase() {
        List<User> list = jdbcUserDao.findAll();
        assertEquals(1, list.size());
    }

    @Test
    @DataSet(value = { "dataset/dataRole.xml",
            "dataset/dataUser.xml" }, strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, cleanBefore = true, tableOrdering = {
            "role", "user" }, executeScriptsBefore = { "sql/DROP.sql",
            "sql/DDL.sql" })
    public void shouldBecomeZeroTheSizeOfTheTableAfterDeletingOneRecord()
            throws IOException {
        User user = getNewUser();
        jdbcUserDao.remove(user);
        List<User> list = jdbcUserDao.findAll();
        assertEquals(0, list.size());
    }

    @Test
    @DataSet(value = { "dataset/dataRole.xml",
            "dataset/dataUser.xml" }, strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, cleanBefore = true, tableOrdering = {
            "role", "user" }, executeScriptsBefore = { "sql/DROP.sql",
            "sql/DDL.sql" })
    public void shouldReturnUserById() {
        User user = jdbcUserDao.findById(1L);
        assertNotNull(user);
    }

    @Test
    @DataSet(value = { "dataset/dataRole.xml",
            "dataset/dataUser.xml" }, strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, cleanBefore = true, tableOrdering = {
            "role", "user" }, executeScriptsBefore = { "sql/DROP.sql",
            "sql/DDL.sql" })
    public void shouldReturnUserByLogin() {
        User user = jdbcUserDao.findByLogin("lumiere");
        assertNotNull(user);
    }

    @Test
    @DataSet(value = { "dataset/dataRole.xml",
            "dataset/dataUser.xml" }, strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, cleanBefore = true, tableOrdering = {
            "role", "user" }, executeScriptsBefore = { "sql/DROP.sql",
            "sql/DDL.sql" })
    public void shouldReturnUserByEmail() {
        User user = jdbcUserDao.findByEmail("vlad@gmail.com");
        assertNotNull(user);
    }

    @Test
    @DataSet(value = { "dataset/dataRole.xml",
            "dataset/dataUser.xml" }, strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, cleanBefore = true, tableOrdering = {
            "role", "user" }, executeScriptsBefore = { "sql/DROP.sql",
            "sql/DDL.sql" })
    public void shouldBeUpdatedLoginAfterUpdatingTheFieldOfTableUser()
            throws IOException {
        User user = jdbcUserDao.findById(1L);
        assertEquals("lumiere", user.getLogin());
        jdbcUserDao.update(getNewUser());
        User user1 = jdbcUserDao.findById(1L);
        assertEquals("vlad", user1.getLogin());
    }

    public User getNewUser() {
        User user = new User();
        user.setId(1L);
        user.setLogin("vlad");
        user.setPassword("123");
        user.setEmail("vlad@gmail.com");
        user.setFirstName("vlad");
        user.setLastName("shakhno");
        user.setBirthday(Date.valueOf("2019-03-20"));
        user.setRoleId(1L);
        return user;
    }
}