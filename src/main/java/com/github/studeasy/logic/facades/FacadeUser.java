package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import com.github.studeasy.logic.utils.PasswordUtils;
import com.github.studeasy.logic.utils.regexUtils;

import java.sql.SQLException;
import java.util.ArrayList;

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
     * Checks if the logs are the same and calls the DAO to add the partner to the DB
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
                if(regexUtils.matches_mail(email)) {
                    if(regexUtils.matches_password(password)) {
                        salt = PasswordUtils.getSalt(30);
                        password = PasswordUtils.generateSecurePassword(password, salt);
                        DAO.submitAddPartner(email, password, firstname, lastname, company, salt);
                    }
                    else{
                        throw new BadInformationException("Password not enough strong ! Please retry");
                    }
                }
                else{
                    throw new BadInformationException("Email not strong enough ! Please retry");
                }
            }
            else{
                throw new BadInformationException("The provided information doesn't match ! Please retry");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Checks if the logs are the same and call the DAO to update a partner
     * @param email
     * @param confirmEmail
     * @param password
     * @param confirmPassword
     * @param firstname
     * @param lastname
     * @param company
     * @param user
     * @throws Exception
     */
    public void submitUpdatePartner(String email, String confirmEmail,String password, String confirmPassword, String firstname, String lastname, String company, Object user) throws Exception {
        String salt;
        try{
            if(email.equals(confirmEmail) &&  password.equals(confirmPassword)){
                if(!password.equals("")) {
                    if (regexUtils.matches_mail(email)) {
                        if (regexUtils.matches_password(password)) {
                            salt = PasswordUtils.getSalt(30);
                            password = PasswordUtils.generateSecurePassword(password, salt);
                            DAO.submitUpdatePartner(email, password, firstname, lastname, company, salt,(User) user);
                        }
                        else{
                            throw new BadInformationException("Password not enough strong ! Please retry");
                        }
                    }
                    else{
                        throw new BadInformationException("Email not strong enough ! Please retry");
                    }
                }
                else{
                    if (regexUtils.matches_mail(email)) {
                        DAO.submitUpdatePartnerNoPassword(email, firstname, lastname, company, (User) user);

                    }
                    else{
                        throw new BadInformationException("Email not strong enough ! Please retry");
                    }
                }
            }
            else{
                throw new BadInformationException("The provided information doesn't match ! Please retry");
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * Get all the partner
     * @return ArrayList of all partner
     */
    public ArrayList<User> getAllPartner() throws Exception {
        return DAO.getAllPartner();
    }

    /**
     * Delete a partner
     */
    public void deletePartner(Object user) throws Exception {
        DAO.deletePartner((User) user);
    }
}