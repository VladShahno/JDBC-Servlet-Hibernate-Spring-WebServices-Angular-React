package com.nixsolutions.test.dao;

import com.nixsolutions.crudapp.util.DataSourceUtil;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;

import javax.sql.DataSource;
import java.io.*;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConfig {
    private String starterTableXml = "src/test/java/resources/dataset/starterTable.xml";
    private String ddlSql = "src/test/java/resources/sql/DDL.sql";
    private DataSource dataSource = DataSourceUtil.getDataSource();

    public DbConfig() throws IOException {
    }

    public void setUp() throws Exception {
        IDatabaseConnection connection = new DatabaseConnection(
                dataSource.getConnection());
        IDataSet iDataSet = new FlatXmlDataSetBuilder().build(
                new File(starterTableXml));
        DatabaseOperation.CLEAN_INSERT.execute(connection, iDataSet);
    }

    public void createTables() {
        try (Connection connection = dataSource.getConnection()) {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    new FileInputStream(ddlSql));
            RunScript.execute(connection, inputStreamReader);
        } catch (SQLException | FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected ITable getExpectedTable(String presetXml, String table)
            throws DataSetException, MalformedURLException {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                new File(presetXml));
        return expectedDataSet.getTable(table);
    }

    protected ITable getActualTable(String table)
            throws SQLException, DatabaseUnitException {
        IDataSet actualDataSet = new DatabaseConnection(
                dataSource.getConnection()).createDataSet();
        return actualDataSet.getTable(table);
    }
}

