package com.github.studeasy.dao.userDAO;

import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.SQLException;
import java.util.ArrayList;

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
     * Method which will register a new user in the database
     * @param firstName first name of the new user
     * @param lastName last Name of the new user
     * @param pseudo pseudo of the new user
     * @param email email of the new user
     * @param password password of the new user
     * @param salt  salt key of the new user
     * @throws SQLException if an error occur
     */
    public abstract void register(String firstName,String lastName,String pseudo,String email,String password, String salt) throws Exception;

    /**
     * method which will delete an user from the db
     * @param id the id of the user to delete
     * @throws Exception if an error occur
     */
    public abstract void deleteUser(int id) throws Exception;

    /**
     * Update information of an user
     * @param firstName the first name of the user
     * @param lastName last name of the user
     * @param pseudo pseudo of the user
     * @param email email of the user
     * @param password password of the user
     * @param salt salt of the user
     * @return the new user
     * @throws Exception if an error occur
     */
    public abstract User update(String firstName, String lastName, String pseudo, String email, String password, String salt) throws Exception;

    /**
     * Get all the users who are student
     * @return An ArrayList of all the students in database
     */
    public abstract ArrayList<User> seeAllUsers();
}