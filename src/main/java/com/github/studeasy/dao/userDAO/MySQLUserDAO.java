package com.github.studeasy.dao.userDAO;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The user DAO using a MySQL database
 * Contains all the methods accessing user related data
 */
public class MySQLUserDAO extends UserDAO{

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLUserDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }

    /**
     * Method asking the database if a user with this mail exist,
     * and returning him if he exists
     * @param email the email to check
     * @return the user corresponding
     * @throws Exception if the user doesn't exist in the database
     */
    public User searchUser(String email) throws Exception{
        User currentUser = null;
        try {
            // We prepare the SQL request to retrieve a user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM user WHERE emailAddress = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, email);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We check if the query retrieved a user
            if (!resultSet.next()) {
                // No, we throw an error
                throw new BadCredentialsException("No user found");
            } else {
                // We create a user according to his role
                currentUser= new User(resultSet.getString(3),resultSet.getString(2),resultSet.getString(6),resultSet.getString(5),resultSet.getInt(4),resultSet.getString(8),resultSet.getString(7),resultSet.getInt(9), resultSet.getString(10));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return currentUser;
    }

    public void register(String firstName,String lastName,String pseudo,String email,String password, String salt) throws Exception{
        try {
            // We prepare the SQL request to insert a user
            PreparedStatement preparedStatement;

            String request = "INSERT INTO user (firstName,lastName,role,password,emailAddress,pseudo,salt) VALUES  (?,?,?,?,?,?,?)";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, 1);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, pseudo);
            preparedStatement.setString(7, salt);
            // We execute the query
            preparedStatement.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
