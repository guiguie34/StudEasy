package com.github.studeasy.dao.commandOfServiceDAO;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.dao.userDAO.UserDAO;
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

            String request = "UPDATE command SET state = 1 where fkUser = ? and fkService = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, c.getOwner().getIdUser());
            preparedStatement.setInt(2, c.getService().getIdService());
            preparedStatement.executeUpdate();
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
            String request = "DELETE from command where fkUser = ? and fkService = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, c.getOwner().getIdUser());
            preparedStatement.setInt(2, c.getService().getIdService());
            // We execute the query
            Service s = c.getService();
            User u = c.getOwner();
            if(s.getTypeService()==0){
                UserDAO userdao = UserDAO.getInstance();
                userdao.addPoints(s.getCost(),u);
            }
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void applyorbuyForService(Service s,Object currentUser) throws Exception {
            // We prepare the SQL request
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;

            String request = "INSERT INTO command(fkUser, fkService, state)"+"VALUES (?,?,?)";
            if(s.getTypeService()==0){
                UserDAO userdao = UserDAO.getInstance();
                userdao.removePoints(s.getCost(),((User)currentUser));
            }
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1,((User)currentUser).getIdUser() );
            preparedStatement.setInt(2, s.getIdService());
            preparedStatement.setInt(3,0);
            // We execute the query
            preparedStatement.executeUpdate();

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

    /***
     * Get list of pending service
     * @param currentUser
     * @return
     */
    public ArrayList<CommandOfService> getServiceBought(User currentUser){
        ArrayList<CommandOfService> servicebought  =new ArrayList<>();
        try{
            // We prepare the SQL request to retrieve the services of the user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM command,service,categorytag,user WHERE fkUser = ?" +
                    "AND command.fkService=service.idService " +
                    "AND service.fkCategory=categorytag.idCategory " +
                    "AND user.idUser=service.ownerService ";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1,currentUser.getIdUser());
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We retrieve all command
            while(resultSet.next()){
                CategoryTag category=new CategoryTag(resultSet.getInt(18),resultSet.getString(19),resultSet.getString(20));
                Timestamp dateCreation =resultSet.getTimestamp(7);

                Service service = new Service(resultSet.getInt(9), resultSet.getString(11),
                        resultSet.getString(12), resultSet.getInt(13), resultSet.getInt(15),
                        currentUser, category, resultSet.getInt(16), dateCreation);

                Feedback feedback = new Feedback(resultSet.getInt(5),resultSet.getString(4),
                        resultSet.getString(6),resultSet.getDate(7),resultSet.getInt(3));

                CommandOfService command = new CommandOfService(feedback,currentUser,service,
                        resultSet.getInt(8),resultSet.getDate(7));

                servicebought.add(command);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return servicebought;
    }

    /***
     * Get list of service bought
     * @param currentUser
     * @return ArrayList of CommandOfService
     */
    public ArrayList<CommandOfService> getMyServicePending(User currentUser){
        ArrayList<CommandOfService> servicebought  =new ArrayList<>();
        try{
            // We prepare the SQL request to retrieve the services of the user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM command,service,categorytag,user WHERE command.fkService=service.idService AND service.fkCategory=categorytag.idCategory AND user.idUser=service.ownerService AND ownerService = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1,currentUser.getIdUser());
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We retrieve all command
            while(resultSet.next()){
                CategoryTag category=new CategoryTag(resultSet.getInt(18),resultSet.getString(19),resultSet.getString(20));
                Timestamp dateCreation =resultSet.getTimestamp(7);

                User owner = new User(resultSet.getInt(21),resultSet.getString(23),resultSet.getString(22),
                        resultSet.getString(26),resultSet.getString(25),resultSet.getInt(24),resultSet.getString(28),
                        resultSet.getString(27),resultSet.getInt(29),resultSet.getString(30));

                Service service = new Service(resultSet.getInt(9), resultSet.getString(11),
                        resultSet.getString(12), resultSet.getInt(13), resultSet.getInt(15),
                        owner, category, resultSet.getInt(16), dateCreation);

                Feedback feedback = new Feedback(resultSet.getInt(5),resultSet.getString(4),
                        resultSet.getString(6),resultSet.getDate(7),resultSet.getInt(3));

                CommandOfService command = new CommandOfService(feedback,currentUser,service,
                        resultSet.getInt(8),resultSet.getDate(7));

                servicebought.add(command);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return servicebought;
    }



}

