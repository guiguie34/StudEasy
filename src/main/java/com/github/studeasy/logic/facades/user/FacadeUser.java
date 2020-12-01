package com.github.studeasy.logic.facades.user;

import com.github.studeasy.dao.DAO;
import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.dao.userDAO.MySQLUserDAO;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.AbstractFacade;

public class FacadeUser extends AbstractFacade {

    protected DAO dao;
    private Session sessionUser;

    private FacadeUser() {
        super();
        this.dao = factory.createUserDAO();
        sessionUser= null;
    }
    private static class createFacadeUser {
        static final FacadeUser INSTANCE = new FacadeUser();
    }


    public static FacadeUser getInstance(){
        return createFacadeUser.INSTANCE;
    }

    public void login(String email, String password) throws Exception {

        User u;

        u = ((MySQLUserDAO) dao).searchUser(email);
        if(u.getPassword().equals(password)) { //change with hash password comparison
            sessionUser = Session.getInstance();
            sessionUser.setCurrentUser(u);
            System.out.println(u.getEmailAdress());
        }
        else{
            throw new BadCredentialsException("Bad Password");
        }

    }
}
