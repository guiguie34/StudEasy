package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.userDAO.UserDAO;

/**
 * Abstract Factory class with contains all the Factory
 */
public abstract class Factory {
    public abstract UserDAO createUserDAO();
}
