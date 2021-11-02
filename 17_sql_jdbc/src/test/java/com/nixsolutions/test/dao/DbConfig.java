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

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConfig {
    private String starterTableXml = "src/test/java/resources/dataset/starterTable.xml";
    private DataSource dataSource = DataSourceUtil.getDataSource();

    public DbConfig() throws IOException {
    }

    public void setUp() throws Exception {
        IDatabaseConnection connection = new DatabaseConnection(
                dataSource.getConnection());
        IDataSet iDataSet = new FlatXmlDataSetBuilder().build(
                new File(starterTableXml));
        DatabaseOperation.CLEAN_INSERT.execute(connection, iDataSet);
        connection.close();
    }

    protected ITable getExpectedTable(String presetXml, String table)
            throws DataSetException, MalformedURLException {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                new File(presetXml));
        return expectedDataSet.getTable(table);
    }

    protected ITable getActualTable(String table)
            throws SQLException, DatabaseUnitException {
        try (Connection connection = dataSource.getConnection()) {
            IDataSet actualDataSet = new DatabaseConnection(
                    connection).createDataSet();
            return actualDataSet.getTable(table);
        }
    }
}

