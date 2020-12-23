package com.github.studeasy.dao.feedbackDAO;

import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Feedback;
import com.github.studeasy.logic.factory.Factory;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Abstract class for the Feedback DAO
 * Contains the methods needed by the Feedback DAO
 */
public abstract class FeedbackDAO {

    /**
     * The singleton of the FeedbackDAO
     */
    private static FeedbackDAO fDAO = null;

    /**
     * Static method which returns the instance of the FeedbackDAO,
     * or ask the factory to create one
     * @return the instance of MySQLFeedbackDAO
     */
    public static FeedbackDAO getInstance(){
        if (fDAO == null){
            Factory factory = Factory.getInstance();
            fDAO = factory.createFeedbackDAO();
        }
        return fDAO;
    }

    /**
     * Method which will get all the feedbacks related to a service
     * @param idService the service concerned
     * @return All the feedbacks in an ArrayList
     */
    public abstract ArrayList<Feedback> seeAllFeedbacks(int idService);

    /**
     * remove the feedback present in the table command
     * @param idFeedback the id of the feedback to delete
     */
    public abstract void deleteFeedback(int idFeedback) throws SQLException;

    /**
     *indicate if the service has been purchased by the user connected
     * @param idService the id of the service concerned
     * @return true if the student has bought the service else false
     */
    public abstract boolean hasCommand(int idService);

    /**
     * add the feedback in database
     * @param title the title of the feedback
     * @param comment the comment of the feedback
     * @param rate the rate of the feedback
     * @param idService the id of the service concerned
     * @throws Exception if an error occur
     */
    public abstract void leaveFeedback(String title, String comment, int rate, int idService) throws Exception;
}
