package com.github.studeasy.dao.feedbackDAO;

import com.github.studeasy.logic.common.Feedback;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.factory.Factory;

import java.sql.*;
import java.util.ArrayList;

/**
 * The Feedback DAO using a MySQL database
 * Contains all the methods accessing feedback related data
 */
public class MySQLFeedbackDAO extends FeedbackDAO{

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLFeedbackDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }

    /**
     * Method which will get all the feedbacks related to a service
     * @param idService the service concerned
     * @return All the feedbacks in an ArrayList
     */
    public ArrayList<Feedback> seeAllFeedbacks(int idService){
        Feedback currentFeedback;
        ArrayList<Feedback> allFeedbacks = new ArrayList<>();
        try {
            // We prepare the SQL request to retrieve a user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT idCommand,titleFeedback,commentFeedback,date,rateFeedback FROM command WHERE fkService = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, idService);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We check if the query retrieved a user
            while(resultSet.next()) {
                //verify if there is a feedback
                if(resultSet.getString(2) != null){
                    // We create a user according to his role
                    currentFeedback = new Feedback(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDate(4),resultSet.getInt(5));
                    allFeedbacks.add(currentFeedback);
                }

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return allFeedbacks;
    }

    /**
     * remove the feedback present in the table command
     * @param idFeedback the id of the feedback to delete
     */
    public void deleteFeedback(int idFeedback) throws SQLException {
        // We prepare the SQL request to update a user
        PreparedStatement preparedStatement;
        String request = "UPDATE command SET titleFeedback = ?,"
                +"commentFeedback = ?,"
                +"rateFeedback = ? " +
                "WHERE idCommand = ?";
        preparedStatement = DB.prepareStatement(request);
        preparedStatement.setInt(4, idFeedback);
        preparedStatement.setString(1, null);
        preparedStatement.setString(2, null);
        preparedStatement.setString(3, null);
        preparedStatement.executeUpdate();
    }

    /**
     *indicate if the service has been purchased by the user connected
     * @param idService the id of the service concerned
     * @return true if the student has bought the service else false
     */
    public boolean hasCommand(int idService){
        int idUser = Session.getInstance().getCurrentUser().getIdUser();
        try {
            // We prepare the SQL request to retrieve a user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM command WHERE fkService = ? AND fkUser = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, idService);
            preparedStatement.setInt(2, idUser);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We check if the result is empty or if there is already a comment left by the current user
            //if the result is empty, it means that the user has never purchased this service
            //so he will not authorized to leave a comment
            return (resultSet.next() && resultSet.getString(3) == null);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * add the feedback in database
     * @param title the title of the feedback
     * @param comment the comment of the feedback
     * @param rate the rate of the feedback
     * @param idService the id of the service concerned
     * @throws Exception if an error occur
     */
    public void leaveFeedback(String title, String comment, int rate, int idService) throws Exception{
        // We prepare the SQL request to insert a user
        PreparedStatement preparedStatement;
        String request2 = "UPDATE user SET firstName= ?, "
                +" lastName = ? ,"
                +" role = ?, "
                + "password = ?, "
                + "emailAddress = ?,"
                + "pseudo = ?,"
                + "salt = ?"
                + "WHERE emailAddress = ?";
        String request = "UPDATE command SET titleFeedback = ? ," +
                "commentFeedback = ? ," +
                "rateFeedback = ? " +
                "WHERE fkUser = ? AND fkService  = ?";
        preparedStatement = DB.prepareStatement(request);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, comment);
        preparedStatement.setInt(3, rate);
        preparedStatement.setInt(4, Session.getInstance().getCurrentUser().getIdUser());
        preparedStatement.setInt(5, idService);
        System.out.println(preparedStatement);
        // We execute the query
        preparedStatement.executeUpdate();
    }
}
