package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.userDAO.UserDAO;

/**
 * Abstract Factory class which contains all the Factory
 */
public abstract class Factory {
    /**
     * Method that will create a UserDAO
     * @return the User DAO
     */
    public abstract UserDAO createUserDAO();
}