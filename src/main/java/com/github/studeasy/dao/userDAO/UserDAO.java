package com.github.studeasy.dao.userDAO;

import com.github.studeasy.logic.common.User;

/**
 * Interface for the User DAO
 * Contains the methods needed by the User DAO
 */
public interface UserDAO {
    /**
     * Method asking the database if a user with those credentials exist,
     * and returning him if he exists
     * @param email, the email to check
     * @param password, the password to check
     * @return the user corresponding
     * @throws Exception if the user doesn't exist in the database
     */
    User loginUser(String email, String password) throws Exception;
}