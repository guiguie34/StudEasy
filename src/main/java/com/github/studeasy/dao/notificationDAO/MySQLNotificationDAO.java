package com.github.studeasy.dao.notificationDAO;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
