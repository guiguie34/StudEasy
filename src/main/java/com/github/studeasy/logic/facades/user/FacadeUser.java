package com.github.studeasy.logic.facades.user;

import com.github.studeasy.dao.DAO;
import com.github.studeasy.dao.userDAO.MySQLUserDAO;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.AbstractFacade;

public class FacadeUser extends AbstractFacade {

    protected DAO dao;
    private Session sessionUser;

    public FacadeUser() {
        super();
        this.dao = factory.createUserDAO();
        sessionUser= null;
    }

    public void login(String email, String password) throws Exception {

        User u;

        u = ((MySQLUserDAO) dao).searchUser(email, password);

        sessionUser = Session.getInstance();
        sessionUser.setCurrentUser(u);
        System.out.println(u.getEmailAdress());

    }
}
