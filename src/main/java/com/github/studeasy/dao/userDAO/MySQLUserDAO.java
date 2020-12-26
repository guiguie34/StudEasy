package com.github.studeasy.dao.userDAO;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                currentUser= new User(resultSet.getInt(1),resultSet.getString(3),resultSet.getString(2),resultSet.getString(6),resultSet.getString(5),resultSet.getInt(4),resultSet.getString(8),resultSet.getString(7),resultSet.getInt(9),resultSet.getString(10));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return currentUser;
    }


    /**
     * Add a partner to the database
     * @param email
     * @param password
     * @param firstname
     * @param lastname
     * @param company
     * @param salt
     * @throws Exception if an error occurs
     */
    public void submitAddPartner(String email,String password, String firstname, String lastname, String company, String salt) throws Exception{
        User currentUser = null;
        try {
            // We prepare the SQL request to retrieve a user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            int resultSet;
            String request = "INSERT INTO user(firstName,lastName,role,password,emailAddress,company,salt) VALUES(?,?,?,?,?,?,?)";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setInt(3, 2);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, company);
            preparedStatement.setString(7, salt);
            // We execute the query
            resultSet = preparedStatement.executeUpdate();
            //currentUser = searchUser(email);
        }
        catch(SQLException e){
            throw e;
        }
    }

    /**
     * Get all the partner from the database
     * @return ArrayList containing all the partner
     */
    public ArrayList<User> getAllPartner() throws Exception {
        List<User> partner = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM user WHERE role= 2";
            preparedStatement = DB.prepareStatement(request);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                partner.add(new User(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2), resultSet.getString(6), resultSet.getString(5), resultSet.getInt(4), resultSet.getString(8), resultSet.getString(7), resultSet.getInt(9), resultSet.getString(10)));
            }
        } catch (Exception e) {
           throw e;
        }
        return ((ArrayList<User>) partner);
    }

    public void deletePartner(Object user) throws Exception{
        try {
            // We prepare the SQL request to retrieve a user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            int resultSet;
            String request = "DELETE  FROM user WHERE emailAddress=?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, ((User) user).getEmailAdress());
            resultSet = preparedStatement.executeUpdate();
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * Update the partner with provided information
     * @param email
     * @param password
     * @param firstname
     * @param lastname
     * @param company
     * @param salt
     * @param user
     * @throws Exception
     */
    public void submitUpdatePartner(String email,String password, String firstname, String lastname, String company, String salt, Object user) throws Exception{
        try {
            // We prepare the SQL request to retrieve a user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            int resultSet; //firstName,lastName,role,password,emailAddress,company,salt
            String request = "UPDATE user SET firstName = ?, lastName= ?, password = ?, emailAddress = ?, company = ?, salt =?  WHERE idUser = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, company);
            preparedStatement.setString(6, salt);
            preparedStatement.setInt(7, ((User)user).getIdUser());

            resultSet = preparedStatement.executeUpdate();
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * Update partner with provided information (except password)
     * @param email
     * @param firstname
     * @param lastname
     * @param company
     * @param user
     * @throws Exception
     */
    public void submitUpdatePartnerNoPassword(String email, String firstname, String lastname, String company, Object user) throws Exception{
        try {
            // We prepare the SQL request to retrieve a user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            int resultSet; //firstName,lastName,role,password,emailAddress,company,salt
            String request = "UPDATE user SET firstName = ?, lastName= ?, emailAddress = ?, company = ?  WHERE idUser = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, company);
            preparedStatement.setInt(5, ((User)user).getIdUser());

            resultSet = preparedStatement.executeUpdate();
        }
        catch (Exception e){
            throw e;
        }
    }

    public User searchUserById(int id) throws Exception{
        User currentUser = null;
        try {
            // We prepare the SQL request to retrieve a user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM user WHERE idUser = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, id);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We check if the query retrieved a user
            if (!resultSet.next()) {
                // No, we throw an error
                throw new BadCredentialsException("No user found");
            } else {
                // We create a user according to his role
                currentUser= new User(resultSet.getInt(1),resultSet.getString(3),resultSet.getString(2),resultSet.getString(6),resultSet.getString(5),resultSet.getInt(4),resultSet.getString(8),resultSet.getString(7),resultSet.getInt(9),resultSet.getString(10));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return currentUser;
    }
}
