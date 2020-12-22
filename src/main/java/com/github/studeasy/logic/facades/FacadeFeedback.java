package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.feedbackDAO.FeedbackDAO;
import com.github.studeasy.logic.common.Feedback;

import java.sql.SQLException;
import java.util.ArrayList;

public class FacadeFeedback {

    /**
     * Singleton of the facade feedback
     */
    private static FacadeFeedback facadeFeedback = null;

    /**
     * The DAO connected to the database
     */
    private final FeedbackDAO DAO;

    /**
     * Constructor of singleton FacadeFeedback
     * Instantiate the factory
     */
    private FacadeFeedback() {
        // We retrieve the UserDao
        this.DAO = FeedbackDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeFeedback
     * @return the instance of FacadeFeedback
     */
    public static FacadeFeedback getInstance(){
        if(facadeFeedback == null){
            facadeFeedback = new FacadeFeedback();
        }
        return facadeFeedback;
    }

    /**
     * Method which will ask to the DAO to get all the feedbacks related to a service
     * @param idService the service concerned
     * @return All the feedbacks in an ArrayList
     */
    public ArrayList<Feedback> seeAllFeedbacks(int idService) {
        //we ask to the DAO to retrieve all the users
        return DAO.seeAllFeedbacks(idService);
    }

    /**
     * Ask to the DAO to delete the feedback
     * @param feedbackToDelete the feedback to delete
     */
    public void deleteFeedback(Feedback feedbackToDelete) throws SQLException {
        DAO.deleteFeedback(feedbackToDelete.getIdFeedback());
    }
}
