package com.nixsolutions.test.dao.impl;

import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.util.DataBaseCreator;
import com.nixsolutions.test.dao.DbConfig;
import com.nixsolutions.crudapp.dao.RoleDao;
import com.nixsolutions.crudapp.dao.impl.JdbcRoleDaoImpl;

import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.dbunit.Assertion.assertEqualsIgnoreCols;
import static org.junit.Assert.assertEquals;

public class RoleDaoImplTest extends DbConfig {

    private static final String[] IGNORE_COLS = { "role_id" };
    private static final String TABLE_ROLES = "ROLE";
    private static final String FIND_ROLE_XML = "src/test/java/resources/dataset/role/find-role.xml";
    private static final String UPDATE_ROLE_XML = "src/test/java/resources/dataset/role/update-role.xml";
    private static final String SAVE_ROLE_XML = "src/test/java/resources/dataset/role/save-role.xml";
    private static final String REMOVE_ROLE_XML = "src/test/java/resources/dataset/role/remove-role.xml";
    private RoleDao roleDao;

    public RoleDaoImplTest() throws IOException {
    }

    @BeforeClass
    public static void ddlOperations() throws Exception {
        DataBaseCreator.createTableSql();
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.roleDao = new JdbcRoleDaoImpl();
    }

    @Test
    public void testRemoveRole() throws Exception {
        roleDao.remove(new Role("ADMIN"));

        ITable actualTable = getActualTable(TABLE_ROLES);
        ITable expectedTable = getExpectedTable(REMOVE_ROLE_XML, TABLE_ROLES);

        assertEqualsIgnoreCols(expectedTable, actualTable, IGNORE_COLS);
    }

    @Test
    public void testCreateRole() throws Exception {
        roleDao.create(new Role("MEM"));

        ITable actualTable = getActualTable(TABLE_ROLES);
        ITable expectedTable = getExpectedTable(SAVE_ROLE_XML, TABLE_ROLES);

        assertEqualsIgnoreCols(expectedTable, actualTable, IGNORE_COLS);
    }

    @Test
    public void testUpdateRole() throws Exception {
        roleDao.update(new Role(1L, "MODERATOR"));

        ITable actualTable = getActualTable(TABLE_ROLES);
        ITable expectedTable = getExpectedTable(UPDATE_ROLE_XML, TABLE_ROLES);

        assertEqualsIgnoreCols(expectedTable, actualTable, IGNORE_COLS);
    }

    @Test
    public void testFindByName() throws Exception {
        Role actual = roleDao.findByName("ADMIN");

        ITable expectedTable = getExpectedTable(FIND_ROLE_XML, TABLE_ROLES);

        assertEquals(expectedTable.getValue(0, "role_name"), actual.getName());
    }
}
