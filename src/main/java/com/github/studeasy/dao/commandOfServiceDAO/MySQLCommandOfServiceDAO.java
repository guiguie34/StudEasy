package com.github.studeasy.dao.commandOfServiceDAO;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.logic.common.*;
import com.github.studeasy.logic.factory.Factory;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * The Command of Service DAO using a MySQL database
 * Contains all the methods accessing Command of Service related data
 */
public class MySQLCommandOfServiceDAO extends CommandOfServiceDAO{

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLCommandOfServiceDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }

    @Override
    public void acceptTransaction(CommandOfService c) throws Exception {
        try {
            // We prepare the SQL request
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;

            String request = "UPDATE command SET state = 1 where fkUser = ? and fkService = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, c.getOwner().getIdUser());
            preparedStatement.setInt(2, c.getService().getIdService());
            // We execute the query
            resultSet = preparedStatement.executeQuery();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void declineTransaction(CommandOfService c) throws Exception {
        try {
            // We prepare the SQL request
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;

            String request = "DELETE from command where fkUser = ? and fkService = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, c.getOwner().getIdUser());
            preparedStatement.setInt(2, c.getService().getIdService());
            // We execute the query
            resultSet = preparedStatement.executeQuery();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void applyForService(Service s,Object currentUser) throws Exception {
        // create a java calendar instance
        Calendar calendar = Calendar.getInstance();
        // get a java date (java.util.Date) from the Calendar instance.
        // this java date will represent the current date, or "now".
        java.util.Date currentDate = calendar.getTime();
        // create a java.sql.Date from the java.util.Date
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        try {
            // We prepare the SQL request
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;

            String request = "INSERT INTO command(fkUser, fkService, date, state)"+"VALUES (?,?,?,?)";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1,((User)currentUser).getIdUser() );
            preparedStatement.setInt(2, s.getIdService());
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4,0);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void buyService(Service s,Object currentUser) throws Exception {
        // create a java calendar instance
        Calendar calendar = Calendar.getInstance();
        // get a java date (java.util.Date) from the Calendar instance.
        // this java date will represent the current date, or "now".
        java.util.Date currentDate = calendar.getTime();
        // create a java.sql.Date from the java.util.Date
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        try {
            // We prepare the SQL request
            PreparedStatement preparedStatement;
            PreparedStatement preparedStatement1;
            // Will contain the result of the query
            ResultSet resultSet;
            ResultSet resultSet1 = null;
            String request1 = "SELECT points from user where emailAddress=?";
            String request = "INSERT INTO command(fkUser, fkService, date, state)"+"VALUES (?,?,?,?)";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement1 = DB.prepareStatement(request1);
            preparedStatement1.setString(1,((User)currentUser).getEmailAddress());
            preparedStatement.setInt(1,((User)currentUser).getIdUser() );
            preparedStatement.setInt(2, s.getIdService());
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4,0);
            // We execute the query
            resultSet1= preparedStatement1.executeQuery();
            ResultSetMetaData resultMeta = resultSet1.getMetaData();
            int pointUser = Integer.parseInt(resultMeta.toString());
            if(pointUser>s.getCost()){
                resultSet = preparedStatement.executeQuery();
            }
            else{
                Exception e = new Exception("No enough points");
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addFeedback(CommandOfService c) throws Exception {
        try {
            // We prepare the SQL request
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "INSERT INTO command(fkUser, fkService, titleFeedback, rateFeedback, commentFeedback, date)"+"VALUES (?,?,?,?,?,?)";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, c.getOwner().getIdUser());
            preparedStatement.setInt(2, c.getService().getIdService());
            preparedStatement.setString(3,c.getFeedback().getTitle());
            preparedStatement.setInt(4,c.getFeedback().getRate());
            preparedStatement.setString(5,c.getFeedback().getComment());
            preparedStatement.setDate(6, (Date) c.getCreationDate());
            // We execute the query
            resultSet = preparedStatement.executeQuery();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<CommandOfService> getServiceBought(User currentUser){
        ArrayList<CommandOfService> servicebought  =new ArrayList<>();
        try{
            // We prepare the SQL request to retrieve the services of the user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM command,service WHERE fkUser = ? AND command.fkService=service.idService";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1,currentUser.getIdUser());
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We retrieve all command
            while(resultSet.next()){
                CategoryTag category=new CategoryTag(resultSet.getString(19),resultSet.getString(20));
                Timestamp dateCreation =resultSet.getTimestamp(6);

                Service service = new Service(resultSet.getInt(9), resultSet.getString(11),
                        resultSet.getString(12), resultSet.getInt(13), resultSet.getInt(7),
                        currentUser, category, resultSet.getInt(8), dateCreation);
                Feedback feedback = new Feedback(resultSet.getInt(10),resultSet.getString(13),
                        resultSet.getString(15),resultSet.getDate(16),resultSet.getInt(12));
                CommandOfService command = new CommandOfService(feedback,currentUser,service,
                        resultSet.getInt(17),resultSet.getDate(16));

                servicebought.add(command);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return servicebought;
    }

}

