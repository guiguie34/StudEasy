package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.DAO;

public interface FactoryI {
    DAO createUserDAO();
}
