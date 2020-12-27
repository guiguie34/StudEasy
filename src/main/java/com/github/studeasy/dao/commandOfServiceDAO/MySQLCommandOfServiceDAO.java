package com.github.studeasy.dao.commandOfServiceDAO;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.*;

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
            String request = "INSERT INTO command(fkUser, fkService, date)"+"VALUES (?,?,?)";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, c.getOwner().getIdUser());
            preparedStatement.setInt(2, c.getService().getIdService());
            preparedStatement.setDate(3, (Date) c.getCreationDate());
            // We execute the query
            resultSet = preparedStatement.executeQuery();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void declineTransaction(CommandOfService c) throws Exception {

    }

    @Override
    public void applyForService(Service s) throws Exception {

    }

    @Override
    public void buyService(Service s) throws Exception {

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
}
