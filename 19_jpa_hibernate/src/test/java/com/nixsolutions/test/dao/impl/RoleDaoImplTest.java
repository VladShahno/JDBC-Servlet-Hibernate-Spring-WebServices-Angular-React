package com.nixsolutions.test.dao.impl;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.dao.impl.HibernateRoleDao;
import com.nixsolutions.crudapp.entity.Role;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DBUnit(caseInsensitiveStrategy = Orthography.UPPERCASE, url = "jdbc:h2:mem:test_mem", driver = "org.h2.Driver", user = "sa")
@RunWith(JUnit4.class)
public class RoleDaoImplTest {

    RoleDao roleDao = new HibernateRoleDao();

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    @Test
    @DataSet(strategy = SeedStrategy.INSERT, cleanBefore = true, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldReturnTheNameOfTheRoleAfterAddingItToTheDatabase() {
        roleDao.create(getNewRole());
        List<Role> list = roleDao.findAll();
        assertEquals(1, list.size());
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldReturnAllRolesFromDatabase() {
        List<Role> list = roleDao.findAll();
        assertEquals(2, list.size());
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldBecomeOneTheSizeOfTheTableAfterDeletingOneRecord() {
        Role role = getNewRole();
        roleDao.remove(role);
        List<Role> list = roleDao.findAll();
        assertEquals(1, list.size());
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldReturnRoleById() {
        Role role = roleDao.findById(1L);
        assertNotNull(role);
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldReturnRoleByName() throws SQLException {
        Role role = roleDao.findByName("bodyPositive");
        assertNotNull(role);
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldBeUpdatedRoleAfterUpdatingTheFieldOfTableRole()
            throws SQLException {
        Role role = roleDao.findById(1L);
        assertEquals("king", role.getName());
        role.setName("not king");
        roleDao.update(role);
        role = roleDao.findByName("not king");
        assertEquals("not king", role.getName());
    }

    private Role getNewRole() {
        return new Role(1L, "king");
    }
}