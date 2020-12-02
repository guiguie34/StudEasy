package com.github.studeasy.logic.facades.user;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;
import com.github.studeasy.logic.factory.MySQLFactory;

/**
 * The Facade User for the UserDAO
 * It contains methods that allows a user to login
 */
public class FacadeUser {

    protected UserDAO dao;

    /**
     * Constructor of singleton FacadeUser
     * Instantiate the factory
     */
    private FacadeUser() {
        Factory factory = MySQLFactory.getInstance();
        this.dao = factory.createUserDAO();
    }

    /**
     * Static function that create a FacadeUser
     */
    private static class createFacadeUser {
        static final FacadeUser INSTANCE = new FacadeUser();
    }

    /**
     * Static function that allows to get the Instance of the FacadeUser
     * @return the instance of FacadeUser
     */
    public static FacadeUser getInstance(){
        return createFacadeUser.INSTANCE;
    }

    /**
     * Function login allows verifier the password of a user, if it's the right password set the user as the currentUser
     * @param email
     * @param password
     * @throws Exception if the password is wrong
     */
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
