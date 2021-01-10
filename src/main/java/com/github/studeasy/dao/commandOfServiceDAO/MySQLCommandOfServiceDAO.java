package com.github.studeasy.dao.commandOfServiceDAO;

import com.github.studeasy.logic.common.*;
import com.github.studeasy.logic.factory.Factory;

import java.sql.*;
import java.util.ArrayList;

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

            String request = "UPDATE command SET state = 1 where fkUser = ? AND fkService = ? AND idCommand = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, c.getOwner().getIdUser());
            preparedStatement.setInt(2, c.getService().getIdService());
            preparedStatement.setInt(3, c.getIdCommand());
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
            String request = "DELETE from command where fkUser = ? AND fkService = ? AND idCommand = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, c.getOwner().getIdUser());
            preparedStatement.setInt(2, c.getService().getIdService());
            preparedStatement.setInt(3, c.getIdCommand());
            // We execute the query
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
            String request = "INSERT INTO command(fkUser, fkService, state)"+"VALUES (?,?,0)";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1,((User)currentUser).getIdUser() );
            preparedStatement.setInt(2, s.getIdService());
            // We execute the query
            preparedStatement.executeUpdate();
    }

    /**
     * Retrieve the command of the user if it exists
     * @param s the service
     * @param u the user
     * @return the command or null if it doesn't exist
     */
    public CommandOfService commandPending(Service s, User u){
        CommandOfService retrievedCommand = null;
        try{
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM command,service,categorytag,user WHERE fkUser = ? " +
                    "AND command.fkService=service.idService " +
                    "AND service.fkCategory=categorytag.idCategory " +
                    "AND user.idUser=service.ownerService " +
                    "AND service.idService = ? " +
                    "AND state = 0";
            // We prepare the SQL request to retrieve the services of the user
            PreparedStatement preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1,u.getIdUser());
            preparedStatement.setInt(2,s.getIdService());

            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We retrieve all command
            if(resultSet.next()){
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

                retrievedCommand = new CommandOfService(resultSet.getInt(1),feedback,u,service,
                        resultSet.getInt(8),resultSet.getTimestamp(7));
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return retrievedCommand;
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

    /**
     * Get list of service bought
     * @param currentUser
     * @return ArrayList of CommandOfService
     */
    public ArrayList<CommandOfService> getServiceBought(User currentUser){
        ArrayList<CommandOfService> servicebought  =new ArrayList<>();
        try{
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM command,service,categorytag,user WHERE fkUser = ? " +
                    "AND command.fkService=service.idService " +
                    "AND service.fkCategory=categorytag.idCategory " +
                    "AND user.idUser=service.ownerService " +
                    "ORDER BY date DESC";
            // We prepare the SQL request to retrieve the services of the user
            PreparedStatement preparedStatement = DB.prepareStatement(request);
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

                CommandOfService command = new CommandOfService(resultSet.getInt(1),feedback,currentUser,service,
                        resultSet.getInt(8),resultSet.getTimestamp(7));

                servicebought.add(command);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return servicebought;
    }


    /***
     * Get list of pending service
     * @param currentUser
     * @return
     */
    public ArrayList<CommandOfService> getMyServicePending(User currentUser){
        ArrayList<CommandOfService> servicebought  =new ArrayList<>();
        try{
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM command,service,categorytag,user WHERE command.fkService=service.idService " +
                    "AND service.fkCategory=categorytag.idCategory " +
                    "AND user.idUser=command.fkUser " +
                    "AND ownerService = ? " +
                    "AND state = 0 " +
                    "ORDER BY dateCreationService DESC";
            // We prepare the SQL request to retrieve the services of the user
            PreparedStatement preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1,currentUser.getIdUser());
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We retrieve all command
            while(resultSet.next()){
                CategoryTag category=new CategoryTag(resultSet.getInt(18),resultSet.getString(19),resultSet.getString(20));
                Timestamp dateCreation =resultSet.getTimestamp(7);

                User buyer = new User(resultSet.getInt(21),resultSet.getString(23),resultSet.getString(22),
                        resultSet.getString(26),resultSet.getString(25),resultSet.getInt(24),resultSet.getString(28),
                        resultSet.getString(27),resultSet.getInt(29),resultSet.getString(30));

                Service service = new Service(resultSet.getInt(9), resultSet.getString(11),
                        resultSet.getString(12), resultSet.getInt(13), resultSet.getInt(15),
                        currentUser, category, resultSet.getInt(16), dateCreation);

                Feedback feedback = new Feedback(resultSet.getInt(5),resultSet.getString(4),
                        resultSet.getString(6),resultSet.getDate(7),resultSet.getInt(3));

                CommandOfService command = new CommandOfService(resultSet.getInt(1),feedback,buyer,service,
                        resultSet.getInt(8),resultSet.getTimestamp(7));

                servicebought.add(command);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return servicebought;
    }
}

