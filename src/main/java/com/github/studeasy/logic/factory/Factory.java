package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.userDAO.UserDAO;

public abstract class Factory {
    public abstract UserDAO createUserDAO();
}
