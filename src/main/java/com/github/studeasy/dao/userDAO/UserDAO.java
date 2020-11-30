package com.github.studeasy.dao.userDAO;

import com.github.studeasy.logic.common.User;

/**
 * Interface for the User DAO
 * Contains the methods needed by the User DAO
 */
public interface UserDAO {
    /**
     * Method asking the database if a user with this mail exist,
     * and returning him if he exists
     * @param email, the email to check
     * @return the user corresponding
     * @throws Exception if the user doesn't exist in the database
     */
    User searchUser(String email) throws Exception;
}