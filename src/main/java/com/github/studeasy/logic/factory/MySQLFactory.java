package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.DAO;
import com.github.studeasy.dao.userDAO.MySQLUserDAO;

public class MySQLFactory implements FactoryI{
    @Override
    public DAO createUserDAO() {
        return new MySQLUserDAO();
    }
}
