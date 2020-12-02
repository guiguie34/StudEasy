package com.github.studeasy.logic.facades.user;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;
import com.github.studeasy.logic.factory.MySQLFactory;

public class FacadeUser {

    protected UserDAO dao;

    private FacadeUser() {
        Factory factory = MySQLFactory.getInstance();
        this.dao = factory.createUserDAO();
    }

    private static class createFacadeUser {
        static final FacadeUser INSTANCE = new FacadeUser();
    }

    public static FacadeUser getInstance(){
        return createFacadeUser.INSTANCE;
    }

    public void  login(String email, String password) throws Exception {

        User u;
        Session sessionUser = Session.getInstance();

        u = dao.searchUser(email);
        if(u.getPassword().equals(password)) { //change with hash password comparison
            sessionUser.setCurrentUser(u);
        }
        else{
            throw new BadCredentialsException("Bad Password");
        }

    }
}
