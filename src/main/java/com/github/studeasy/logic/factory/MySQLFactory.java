package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.userDAO.MySQLUserDAO;
import com.github.studeasy.dao.userDAO.UserDAO;

public class MySQLFactory extends Factory {

    private MySQLFactory(){}

    private static class createMySQLFactory{
        static final MySQLFactory INSTANCE = new MySQLFactory();
    }

    public static MySQLFactory getInstance(){
        return createMySQLFactory.INSTANCE;
    }

    @Override
    public UserDAO createUserDAO() {
        return new MySQLUserDAO();
    }
}
