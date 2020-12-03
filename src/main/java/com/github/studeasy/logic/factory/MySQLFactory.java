package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.db.MySQLConnectionUtil;
import com.github.studeasy.dao.userDAO.MySQLUserDAO;
import com.github.studeasy.dao.userDAO.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The factory for MySQL DAOs
 */
public class MySQLFactory extends Factory {

    private Connection db;

    /**
     * Default constructor of a singleton MySQLFactory
     */
    private MySQLFactory(){
        this.openConnection();
    }

    /**
     * Create the connection to the database
     */
    private void openConnection(){
        MySQLConnectionUtil connection = MySQLConnectionUtil.getInstance();
        this.db = connection.getDb();
    }

    /**
     * Close the connection
     * @throws SQLException
     */
    private void closeConnection() throws SQLException {
        this.db.close();
    }

    /**
     * Lazy holder which contains the objet MySQLFactory
     */
    private static class createMySQLFactory{
        static final MySQLFactory INSTANCE = new MySQLFactory();
    }

    /**
     * Static method which returns the instance of the MySQLFactory
     * @return the instance of MySQLFactory
     */
    public static MySQLFactory getInstance(){
        return createMySQLFactory.INSTANCE;
    }

    /**
     * To get the connection to the database
     * @return the connection to the database
     */
    public Connection getDb() {
        return db;
    }

    /**
     * Method which will create a MySQLUserDAO
     * @return the MySQLUserDAO
     */
    @Override
    public UserDAO createUserDAO() {
        return MySQLUserDAO.getInstance();
    }
}