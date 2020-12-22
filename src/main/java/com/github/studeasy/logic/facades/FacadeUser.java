package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import com.github.studeasy.logic.utils.PasswordUtils;

/**
 * The Facade User for the UserDAO
 * It contains methods that allow a user to login
 */
public class FacadeUser {

    /**
     * Singleton of the facade user
     */
    private static FacadeUser facadeUser = null;

    /**
     * The DAO connected to the database
     */
    private final UserDAO DAO;

    /**
     * Constructor of singleton FacadeUser
     * Instantiate the factory
     */
    private FacadeUser() {
        // We retrieve the UserDao
        this.DAO = UserDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeUser
     * @return the instance of FacadeUser
     */
    public static FacadeUser getInstance(){
        if(facadeUser == null){
            facadeUser = new FacadeUser();
        }
        return facadeUser;
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

    /**
     * Check if the logs are the same
     * TODO: See for strength of password
     * @param email
     * @param confirmEmail
     * @param password
     * @param confirmPassword
     * @param firstname
     * @param lastname
     * @param company
     * @throws Exception
     */
    public void submitAddPartner(String email, String confirmEmail,String password, String confirmPassword, String firstname, String lastname, String company) throws Exception {
        String salt;
        try {
            if(email.equals(confirmEmail) &&  password.equals(confirmPassword)) {
                salt = PasswordUtils.getSalt(30);
                password = PasswordUtils.generateSecurePassword(password, salt);
                DAO.submitAddPartner(email, password, firstname, lastname, company, salt);
            }
            else{
                throw new BadInformationException("The provided information doesn't match ! Please retry");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}