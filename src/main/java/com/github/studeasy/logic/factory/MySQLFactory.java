package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.DAO;
import com.github.studeasy.dao.userDAO.MySQLUserDAO;

public class MySQLFactory implements FactoryI{

    private MySQLFactory(){}

    private static class createMySQLFactory{
        static final MySQLFactory INSTANCE = new MySQLFactory();
    }

    public static MySQLFactory getInstance(){
        return createMySQLFactory.INSTANCE;
    }

    @Override
    public DAO createUserDAO() {
        return new MySQLUserDAO();
    }
}
