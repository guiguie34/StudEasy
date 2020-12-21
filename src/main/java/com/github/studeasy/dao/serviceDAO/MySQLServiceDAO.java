package com.github.studeasy.dao.serviceDAO;

import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;

/**
 * The Service DAO using a MySQL database
 * Contains all the methods accessing service related data
 */
public class MySQLServiceDAO extends ServiceDAO {

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLServiceDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }
}
