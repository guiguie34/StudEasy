package com.github.studeasy.logic.facades.user;

import com.github.studeasy.dao.DAO;
import com.github.studeasy.dao.userDAO.MySQLUserDAO;
import com.github.studeasy.logic.facades.AbstractFacade;

public class FacadeUser extends AbstractFacade {

    protected DAO dao;

    public FacadeUser() {
        super();
        this.dao = factory.createUserDAO();
    }

    public void login(String email, String password){
        ((MySQLUserDAO)dao).searchUser(email,password);
    }
}
