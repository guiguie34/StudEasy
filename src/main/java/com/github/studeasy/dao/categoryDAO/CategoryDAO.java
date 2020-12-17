package com.github.studeasy.dao.categoryDAO;

import com.github.studeasy.logic.factory.Factory;

/**
 * Abstract class for the Category DAO
 * Contains the methods needed by the Category DAO
 */
public abstract class CategoryDAO {
    /**
     * The singleton of the CategoryDAO
     */
    private static CategoryDAO categoryDAO = null;

    /**
     * Static method which returns the instance of the CategoryDAO,
     * or ask the factory to create one
     * @return the instance of MySQLUserDAO
     */
    public static CategoryDAO getInstance(){
        if (categoryDAO == null){
            Factory factory = Factory.getInstance();
            categoryDAO = factory.createCategoryDAO();
        }
        return categoryDAO;
    }
}
