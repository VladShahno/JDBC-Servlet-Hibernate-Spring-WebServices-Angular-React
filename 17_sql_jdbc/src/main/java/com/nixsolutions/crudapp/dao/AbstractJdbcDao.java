package com.nixsolutions.crudapp.dao;

import com.nixsolutions.crudapp.util.DataSourceUtil;

import java.sql.Connection;

// AsbtractName но класс не абстрактный.
// На схеме классов его вообще нет.
// Разве нет возможности вынести хоть какой то метод в дженерик парент? Update, remove, findById, findAll, Create точно должно получится
public class AbstractJdbcDao {
    // Ты создаешь коннекшн пул каждый раз когда юзер просить коннекшн. Абсолютно неверное использование технологии
    public Connection createConnection() {
        return new DataSourceUtil().getConnection();
    }
}
