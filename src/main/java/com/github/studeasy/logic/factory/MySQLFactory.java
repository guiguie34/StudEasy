package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.userDAO.MySQLUserDAO;
import com.github.studeasy.dao.userDAO.UserDAO;

/**
 * The factory for MySQL DAOs
 */
public class MySQLFactory extends Factory {

    /**
     * Default constructor of a singleton MySQLFactory
     */
    private MySQLFactory(){}

    /**
     * Lazy holder which contains the objet MySQLFactory
     */
    private static class createMySQLFactory{
        static final MySQLFactory INSTANCE = new MySQLFactory();
    }

    /**
     * Static methode which return the instance of the MySQLFactory
     * @return the instance of MySQLFactory
     */
    public static MySQLFactory getInstance(){
        return createMySQLFactory.INSTANCE;
    }

    /**
     * Method which will create a MySQLUserDAO
     * @return the MySQLUserDAO
     */
    @Override
    public UserDAO createUserDAO() {
        return new MySQLUserDAO();
    }
}