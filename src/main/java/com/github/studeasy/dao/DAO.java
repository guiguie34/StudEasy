package com.github.studeasy.dao;

import com.github.studeasy.dao.db.ConnectionUtilI;
import com.github.studeasy.dao.db.MySQLConnectionUtil;

import java.sql.Connection;

public abstract class DAO {

    protected Connection db;

    public DAO() {
        ConnectionUtilI connection = MySQLConnectionUtil.getInstance();
        this.db = connection.getDb();
    }
}