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

    /***
     * Method that asking the database to add points to a user
     * @param nbPoints
     * @param user
     * @throws Exception
     */
    public abstract void addPoints(int nbPoints,User user) throws Exception;

    /***
     * Methode that asking the database to remove points from a user
     * @param nbPoints
     * @param user
     * @throws Exception
     */
    public abstract void removePoints(int nbPoints,User user) throws Exception;

    /***
     * Methode that allows to see points of a user
     * @param user
     * @return nombre de points
     * @throws Exception
     */
    public abstract int viewPoints(User user) throws Exception;

    /**
     * Method which will register a new user in the database
     * @param firstName first name of the new user
     * @param lastName last Name of the new user
     * @param pseudo pseudo of the new user
     * @param email email of the new user
     * @param password password of the new user
     * @param salt  salt key of the new user
     * @param key key sent by email
     * @throws SQLException if an error occur
     */
    public abstract void register(String firstName,String lastName,String pseudo,String email,String password, String salt, String key) throws Exception;

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

    /**
     * Method who will confirm the account
     * @param email the email of the user to confirm
     * @param key key entered by the user
     * @return true if the account is confirmed false otherwise
     */
    public abstract boolean confirmAccount(String email,String key) throws Exception;

    /**
     * Verify if an user is confirmed
     * @param email the email of the user
     * @return true if the user is confirmed false otherwise
     * @throws Exception if an error occur
     */
    public abstract boolean isConfirmed(String email) throws Exception;

    /**
     * Method asking the database if a user with this id exist,
     * and returning him if he exists
     * @param id the id to check
     * @return the user corresponding
     * @throws Exception if the user doesn't exist in the database
     */
    public abstract User searchUserById(int id) throws Exception;


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

    /**
     * Get all the partner from the database
     * @return ArrayList containing all the partner
     */
    public abstract ArrayList<User> getAllPartner() throws Exception;

    /**
     * Delete the partner from the DB
     * @param user The user to be deleted
     * @throws Exception if an error occurs
     */
    public abstract void deletePartner(Object user) throws Exception;

    /**
     * Update the partner without change the password
     * @param email
     * @param firstname
     * @param lastname
     * @param company
     * @throws Exception
     */
    public abstract void submitUpdatePartnerNoPassword(String email, String firstname, String lastname, String company,Object user) throws Exception;

    /**
     * Update with password change
     * @param email
     * @param password
     * @param firstname
     * @param lastname
     * @param company
     * @param salt
     * @throws Exception
     */
    public abstract void submitUpdatePartner(String email,String password, String firstname, String lastname, String company, String salt, Object user) throws Exception;

}