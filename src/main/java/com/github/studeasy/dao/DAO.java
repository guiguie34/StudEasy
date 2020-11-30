package com.github.studeasy.dao;

import com.github.studeasy.dao.db.ConnectionUtilI;
import com.github.studeasy.dao.db.MySQLConnectionUtil;

import java.sql.Connection;

/**
 * Abstract class for the DAOs
 * It's the parent class for all the DAOs, they all contain
 * the connection to the database
 */
public abstract class DAO {

    /**
     * The connection to the database
     */
    protected Connection db;

    /**
     * Instantiate the connection db
     */
    public DAO() {
        ConnectionUtilI connection = MySQLConnectionUtil.getInstance();
        this.db = connection.getDb();
    }
}