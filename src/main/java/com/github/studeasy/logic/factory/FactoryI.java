package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.userDAO.UserDAO;

public interface FactoryI {
    UserDAO createUserDAO();
}
