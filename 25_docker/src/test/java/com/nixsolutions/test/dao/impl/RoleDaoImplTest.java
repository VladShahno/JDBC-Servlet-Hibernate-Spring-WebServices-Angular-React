package com.nixsolutions.test.dao.impl;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.nixsolutions.crudapp.dao.impl.AbstractJdbcDao;
import com.nixsolutions.crudapp.dao.impl.JdbcRoleDaoImpl;
import com.nixsolutions.crudapp.entity.Role;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class RoleDaoImplTest extends AbstractJdbcDao {

    JdbcRoleDaoImpl jdbcRoleDao = new JdbcRoleDaoImpl();

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(getConnection());

    public RoleDaoImplTest() throws IOException {
    }

    @Test
    @DataSet(strategy = SeedStrategy.INSERT, cleanBefore = true, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldReturnTheNameOfTheRoleAfterAddingItToTheDatabase()
            throws IOException {
        jdbcRoleDao.create(getNewRole());
        List<Role> list = jdbcRoleDao.findAll();
        assertEquals(1, list.size());
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldReturnAllRolesFromDatabase() {
        List<Role> list = jdbcRoleDao.findAll();
        assertEquals(2, list.size());
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldBecomeOneTheSizeOfTheTableAfterDeletingOneRecord()
            throws IOException {
        Role role = getNewRole();
        jdbcRoleDao.remove(role);
        List<Role> list = jdbcRoleDao.findAll();
        assertEquals(1, list.size());
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldReturnRoleById() {
        Role role = jdbcRoleDao.findById(1L);
        assertNotNull(role);
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldReturnRoleByName() throws SQLException {
        Role role = jdbcRoleDao.findByName("bodyPositive");
        assertNotNull(role);
    }

    @Test
    @DataSet(value = "dataset/dataRole.xml", strategy = SeedStrategy.CLEAN_INSERT, cleanAfter = true, executeScriptsBefore = {
            "sql/DROP.sql", "sql/DDL.sql" })
    public void shouldBeUpdatedRoleAfterUpdatingTheFieldOfTableRole()
            throws SQLException, IOException {
        Role role = jdbcRoleDao.findById(1L);
        assertEquals("king", role.getName());
        role.setName("not king");
        jdbcRoleDao.update(role);
        role = jdbcRoleDao.findByName("not king");
        assertEquals("not king", role.getName());
    }

    private Role getNewRole() {
        return new Role(1L, "king");
    }
}