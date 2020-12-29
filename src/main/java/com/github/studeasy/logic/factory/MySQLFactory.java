package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.categoryDAO.CategoryDAO;
import com.github.studeasy.dao.categoryDAO.MySQLCategoryDAO;
import com.github.studeasy.dao.commandOfServiceDAO.CommandOfServiceDAO;
import com.github.studeasy.dao.commandOfServiceDAO.MySQLCommandOfServiceDAO;
import com.github.studeasy.dao.db.MySQLConnectionUtil;
import com.github.studeasy.dao.notificationDAO.MySQLNotificationDAO;
import com.github.studeasy.dao.notificationDAO.NotificationDAO;
import com.github.studeasy.dao.serviceDAO.MySQLServiceDAO;
import com.github.studeasy.dao.serviceDAO.ServiceDAO;
import com.github.studeasy.dao.jobDAO.JobDAO;
import com.github.studeasy.dao.jobDAO.MySQLJobDAO;
import com.github.studeasy.dao.feedbackDAO.FeedbackDAO;
import com.github.studeasy.dao.feedbackDAO.MySQLFeedbackDAO;
import com.github.studeasy.dao.userDAO.MySQLUserDAO;
import com.github.studeasy.dao.userDAO.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The factory for MySQL DAOs
 */
public class MySQLFactory extends Factory {

    /**
     * The connection to the database
     */
    private Connection db;

    /**
     * Default constructor of a MySQLFactory
     */
    public MySQLFactory() {
        this.openConnection();
    }

    /**
     * Create the connection to the database
     */
    private void openConnection() {
        MySQLConnectionUtil connection = MySQLConnectionUtil.getInstance();
        this.db = connection.getDb();
    }

    /**
     * Close the connection
     *
     * @throws SQLException if an error occur
     */
    private void closeConnection() throws SQLException {
        this.db.close();
    }

    /**
     * To get the connection to the database
     *
     * @return the connection to the database
     */
    public Connection getDb() {
        return db;
    }

    /**
     * Method which will create a MySQLUserDAO
     *
     * @return the MySQLUserDAO
     */
    @Override
    public UserDAO createUserDAO() {
        return new MySQLUserDAO();
    }

    @Override
    public FeedbackDAO createFeedbackDAO() {
        return new MySQLFeedbackDAO();
    }

    /**
     * Method which will create a MySQLJobDAO
     *
     * @return the MySQLJobDAO
     */
    @Override
    public JobDAO createJobDAO() {
        return new MySQLJobDAO();
    }

    /***
     * Method that will create a MySQLCommandOfServiceDAO
     * @return the MySQLCommandOfServiceDAO
     */
    @Override
    public CommandOfServiceDAO createCommandOfServiceDAO() {
        return new MySQLCommandOfServiceDAO();
    }

    /**
     * Method which will create a MySQLCategoryDAO
     *
     * @return the MySQLCategoryDAO
     */
    @Override
    public CategoryDAO createCategoryDAO() {
        return new MySQLCategoryDAO();
    }

    /**
     * Method which will create a MySQLServiceDAO
     *
     * @return the MySQLServiceDAO
     */
    @Override
    public ServiceDAO createServiceDAO() {
        return new MySQLServiceDAO();
    }

    /**
     * Method that will create a NotificationDAO
     *
     * @return the NotificationDAO
     */
    @Override
    public NotificationDAO createNotificationDAO() {
        return new MySQLNotificationDAO();
    }
}

