package com.github.studeasy.logic.factory;

import com.github.studeasy.dao.categoryDAO.CategoryDAO;
import com.github.studeasy.dao.couponDAO.CouponDAO;
import com.github.studeasy.dao.serviceDAO.ServiceDAO;
import com.github.studeasy.dao.jobDAO.JobDAO;
import com.github.studeasy.dao.feedbackDAO.FeedbackDAO;
import com.github.studeasy.dao.commandOfServiceDAO.CommandOfServiceDAO;
import com.github.studeasy.dao.userDAO.UserDAO;

import java.sql.Connection;

/**
 * Abstract Factory class which contains all the Factories
 */
public abstract class Factory {

    /**
     * Singleton of the factory
     */
    private static Factory factory = null;

    /**
     * Static method which returns the instance of the MySQLFactory
     * @return the instance of MySQLFactory
     */
    public static Factory getInstance(){
        if(factory == null){
            factory = new MySQLFactory();
        }
        return factory;
    }

    /**
     * To get the connection to the database
     * @return the connection to the database
     */
    public abstract Connection getDb();

    /**
     * Method that will create a UserDAO
     * @return the User DAO
     */
    public abstract UserDAO createUserDAO();

    /***
     * Method that will create a CommandOfServiceDAO
     * @return CommandOfServiceDAO
     */
    public abstract CommandOfServiceDAO createCommandOfServiceDAO();

    /**
     * Method that will create a JobDAO
     * @return the Job DAO
     */
    public abstract JobDAO createJobDAO();

    /**
     * Method that will create a FeedbackDAO
     * @return the User DAO
     */
    public abstract FeedbackDAO createFeedbackDAO();

    /**
     * Method that will create a CategoryDAO
     * @return the Category DAO
     */
    public abstract CategoryDAO createCategoryDAO();

    /**
     * Method that will create a ServiceDAO
     * @return the Service DAO
     */
    public abstract ServiceDAO createServiceDAO();

    /**
     * Method that will create a CouponDAO
     * @return the Coupon DAO
     */
    public abstract CouponDAO createCouponDAO();
}