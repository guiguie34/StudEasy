package com.github.studeasy.dao.userDAO;

import com.github.studeasy.dao.db.ConnectionUtilI;
import com.github.studeasy.dao.db.MySQLConnectionUtil;
import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.logic.common.User;

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
    protected Connection db;

    /**
     * Instantiate the connection db
     */
    public MySQLUserDAO() {
        ConnectionUtilI connection = MySQLConnectionUtil.getInstance();
        this.db = connection.getDb();
    }

    /**
     * Method asking the database if a user with this mail exist,
     * and returning him if he exists
     * @param email, the email to check
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
            preparedStatement = db.prepareStatement(request);
            preparedStatement.setString(1, email);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We check if the query retrieved a user
            if (!resultSet.next()) {
                // No, we throw an error
                throw new BadCredentialsException("No user found");
            } else {
                // We create a user according to his role
                currentUser= new User(resultSet.getString(3),resultSet.getString(2),resultSet.getString(6),resultSet.getString(5),resultSet.getInt(4),resultSet.getString(8),resultSet.getString(7));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return currentUser;
    }
}
