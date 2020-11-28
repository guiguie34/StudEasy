package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.userDAO.MySQLUserDAO;
import com.github.studeasy.dao.userDAO.UserDAO;

public class MySQLFactory implements FactoryI{
    @Override
    public UserDAO createUserDAO() {
        return new MySQLUserDAO();
    }
}
