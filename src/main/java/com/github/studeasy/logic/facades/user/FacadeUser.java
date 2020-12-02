package com.github.studeasy.logic.facades.user;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;
import com.github.studeasy.logic.factory.MySQLFactory;

/**
 * The Facade User for the UserDAO
 * It contains methods that allow a user to login
 */
public class FacadeUser {

    /**
     * The DAO connected to the database
     */
    private final UserDAO DAO;

    /**
     * Constructor of singleton FacadeUser
     * Instantiate the factory
     */
    private FacadeUser() {
        // We ask the factory to instantiate the DAO
        Factory factory = MySQLFactory.getInstance();
        this.DAO = factory.createUserDAO();
    }

    /**
     * Lazy holder that create a FacadeUser
     */
    private static class createFacadeUser {
        static final FacadeUser INSTANCE = new FacadeUser();
    }

    /**
     * Static function that allow to get the Instance of the FacadeUser
     * @return the instance of FacadeUser
     */
    public static FacadeUser getInstance(){
        return createFacadeUser.INSTANCE;
    }

    /**
     * Function login allows to check the password of a user,
     * if it's the right password set the user as the currentUser
     * @param email the mail of the user
     * @param password the password of the user
     * @throws Exception if the password or the email is wrong
     */
    public void  login(String email, String password) throws Exception {
        User u;
        Session sessionUser = Session.getInstance();
        u = DAO.searchUser(email);
        if(u.getPassword().equals(password)) { //change with hash password comparison
            sessionUser.setCurrentUser(u);
        }
        else{
            throw new BadCredentialsException("Bad Password");
        }
    }
}
