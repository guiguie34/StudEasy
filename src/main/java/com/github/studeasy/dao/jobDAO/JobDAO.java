package com.github.studeasy.dao.jobDAO;

import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.time.LocalDate;

/**
 * Interface for the Job DAO
 * Contains the methods needed by the Job DAO
 */
public abstract class JobDAO {

    /**
     * The singleton of the JobDAO
     */
    private static JobDAO jDAO = null;

    /**
     * Static method which returns the instance of the UserDAO,
     * or ask the factory to create one
     * @return the instance of MySQLUserDAO
     */
    public static JobDAO getInstance(){
        if (jDAO == null){
            Factory factory = Factory.getInstance();
            jDAO = factory.createJobDAO();
        }
        return jDAO;
    }

    /**
     * Add Job to the DB
     * @throws Exception
     */
    public abstract void addJob(String title, String location, String role, String duration, String mail, String phone, LocalDate localDate, String description, Object currentUser) throws Exception;
}
