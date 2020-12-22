package com.github.studeasy.dao.userDAO;

import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

/**
 * Abstract class for the User DAO
 * Contains the methods needed by the User DAO
 */
public abstract class UserDAO {

    /**
     * The singleton of the UserDAO
     */
    private static UserDAO uDAO = null;

    /**
     * Static method which returns the instance of the UserDAO,
     * or ask the factory to create one
     * @return the instance of MySQLUserDAO
     */
    public static UserDAO getInstance(){
        if (uDAO == null){
            Factory factory = Factory.getInstance();
            uDAO = factory.createUserDAO();
        }
        return uDAO;
    }

    /**
     * Method asking the database if a user with this mail exist,
     * and returning him if he exists
     * @param email the email to check
     * @return the user corresponding
     * @throws Exception if the user doesn't exist in the database
     */
    public abstract User searchUser(String email) throws Exception;


    /**
     * Add a partner to the database
     * @param email
     * @param password
     * @param firstname
     * @param lastname
     * @param company
     * @param salt
     * @throws Exception if an error occurs
     */
    public abstract void submitAddPartner(String email,String password, String firstname, String lastname, String company, String salt) throws Exception;
}