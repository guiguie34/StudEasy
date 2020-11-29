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

    public void login(String email, String password){

        User u= null;

        try {
            u = ((MySQLUserDAO)dao).loginUser(email,password);
            sessionUser = Session.getInstance();
            sessionUser.setCurrentUser(u);
            System.out.println(u.getEmailAdress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isAdmin(){
        sessionUser = Session.getInstance();
        return (sessionUser.getCurrentUser().getRole() == 0);
    }

    public boolean isStudent(){
        sessionUser = Session.getInstance();
        return (sessionUser.getCurrentUser().getRole() == 1);
    }

    public boolean isPartner(){
        sessionUser = Session.getInstance();
        return (sessionUser.getCurrentUser().getRole() == 2);
    }
}
