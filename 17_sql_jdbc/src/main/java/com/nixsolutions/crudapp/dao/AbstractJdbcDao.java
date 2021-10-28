package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.util.DataSourceUtil;

import java.sql.Connection;

public class AbstractJdbcDao {
    public Connection createConnection() {
        return new DataSourceUtil().getConnection();
    }
}
