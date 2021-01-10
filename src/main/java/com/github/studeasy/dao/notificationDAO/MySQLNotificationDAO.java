package com.github.studeasy.dao.notificationDAO;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Notification;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The notification DAO using a MySQL database
 * Contains all the methods accessing user related data
 */
public class MySQLNotificationDAO extends NotificationDAO{

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLNotificationDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }

    /**
     * Get number of notifications for one user
     *
     * @param user User
     * @throws Exception if an error occurs
     */
    @Override
    public int getNbNotif(Object user) throws Exception {
        int nb = 0;

        PreparedStatement preparedStatement;
        // Will contain the result of the query
        ResultSet resultSet;
        String request = "SELECT * FROM notification WHERE ownerNotification = ? AND readNotification = 0";
        preparedStatement = DB.prepareStatement(request);
        preparedStatement.setInt(1, ((User)user).getIdUser());
        // We execute the query
        resultSet = preparedStatement.executeQuery();
        // We check if the query retrieved a user
        while(resultSet.next()) {
            nb = nb+1;
        }
        return nb;
    }

    /**
     * All notifications for on user
     *
     * @param currentUser the user
     * @return all the notifications for the user
     */
    @Override
    public ArrayList<Notification> getNotification(Object currentUser) throws Exception{
        UserDAO uDAO = UserDAO.getInstance();
        List<Notification> notifications = new ArrayList<>();
        PreparedStatement preparedStatement;
        // Will contain the result of the query
        ResultSet resultSet;
        String request = "SELECT * FROM notification WHERE ownerNotification = ? ORDER BY idNotification desc";
        preparedStatement = DB.prepareStatement(request);
        preparedStatement.setInt(1, ((User)currentUser).getIdUser());
        // We execute the query
        resultSet = preparedStatement.executeQuery();
        // We check if the query retrieved a user
        while(resultSet.next()) {
            notifications.add(new Notification(resultSet.getInt(1),resultSet.getString(3), resultSet.getString(4), resultSet.getBoolean(5), uDAO.searchUserById(resultSet.getInt(2)) ));
        }

        return (ArrayList<Notification>) notifications;
    }

    /**
     * Delete notification
     *
     * @param idNotif id from notification to delete
     */
    @Override
    public void deleteNotification(int idNotif) throws Exception {
        PreparedStatement preparedStatement;
        String request = "DELETE FROM notification WHERE idNotification = ? ";
        preparedStatement = DB.prepareStatement(request);
        preparedStatement.setInt(1, idNotif);
        preparedStatement.executeUpdate();
    }

    /**
     * Mark as read the notification in the DB
     *
     * @param idNotif id to mark as read
     * @throws Exception if an error occurs
     */
    @Override
    public void markAsRead(int idNotif) throws Exception {
        PreparedStatement preparedStatement;
        String request = "UPDATE notification SET readNotification=1 WHERE idNotification = ? ";
        preparedStatement = DB.prepareStatement(request);
        preparedStatement.setInt(1, idNotif);
        preparedStatement.executeUpdate();
    }

    /**
     * Create a notification
     *
     * @param idOwner     owner of notification
     * @param title       notification title
     * @param description description
     * @throws Exception if an error occurs
     */
    @Override
    public void createNotification(int idOwner, String title, String description) throws Exception {
        PreparedStatement preparedStatement;
        String request = "INSERT INTO notification(ownerNotification,titleNotification,descriptionNotification) VALUES(?,?,?)";
        preparedStatement = DB.prepareStatement(request);
        preparedStatement.setInt(1,idOwner);
        preparedStatement.setString(2,title);
        preparedStatement.setString(3,description);
        preparedStatement.executeUpdate();

    }
}
