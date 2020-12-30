package com.github.studeasy.dao.notificationDAO;

import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Notification;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.SQLException;
import java.util.ArrayList;

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


    /**
     * All notifications for on user
     * @param currentUser the user
     * @return all the notifications for the user
     */
    public abstract ArrayList<Notification> getNotification(Object currentUser) throws Exception;

    /**
     * Delete notification in the DB
     * @param idNotif id to delete
     * @throws Exception if an error occurs
     */
    public abstract void deleteNotification(int idNotif) throws Exception;

    /**
     * Mark as read the notification in the DB
     * @param idNotif id to mark as read
     * @throws Exception if an error occurs
     */
    public abstract void markAsRead(int idNotif) throws Exception;

    /**
     * Create a notification
     * @param idOwner owner of notification
     * @param title notification title
     * @param description description
     * @throws Exception if an error occurs
     */
    public abstract void createNotification(int idOwner, String title, String description) throws Exception;
}
