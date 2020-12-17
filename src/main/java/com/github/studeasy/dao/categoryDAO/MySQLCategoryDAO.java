package com.github.studeasy.dao.categoryDAO;

import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;

/**
 * The Category DAO using a MySQL database
 * Contains all the methods accessing category related data
 */
public class MySQLCategoryDAO extends CategoryDAO{

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLCategoryDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }
}
