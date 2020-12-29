package com.github.studeasy.dao.notificationDAO;

import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.factory.Factory;

/**
 * Interface for the Notification DAO
 * Contains the methods needed by the Notification DAO
 */
public abstract class NotificationDAO {


    /**
     * The singleton of the UserDAO
     */
    private static NotificationDAO nDAO = null;

    /**
     * Static method which returns the instance of the UserDAO,
     * or ask the factory to create one
     * @return the instance of MySQLUserDAO
     */
    public static synchronized NotificationDAO getInstance(){
        if (nDAO == null){
            Factory factory = Factory.getInstance();
            nDAO = factory.createNotificationDAO();
        }
        return nDAO;
    }

    /**
     * Get number of notifications for one user
     * @param user User
     * @throws Exception if an error occurs
     */
    public abstract int getNbNotif(Object user) throws Exception;
}
