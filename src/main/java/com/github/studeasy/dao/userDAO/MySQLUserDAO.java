package com.github.studeasy.dao.userDAO;

import com.github.studeasy.dao.DAO;
import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.logic.common.Student;
import com.github.studeasy.logic.common.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The user DAO using a MySQL database
 * Contains all the methods accessing user related data
 */
public class MySQLUserDAO extends DAO implements UserDAO{

    /**
     * Method asking the database if a user with those credentials exist,
     * and returning him if he exists
     * @param email, the email to check
     * @param password, the password to check
     * @return the user corresponding
     * @throws Exception if the user doesn't exist in the database
     */
    public User searchUser(String email, String password) throws Exception{
        User currentUser=null;
        try {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String request = "SELECT * FROM user WHERE emailAddress = ?";
            preparedStatement = db.prepareStatement(request);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {

                throw new BadCredentialsException("No user found");
            } else {
                if(resultSet.getInt(4)==0){//Admin
                    currentUser = new User(resultSet.getString(3),resultSet.getString(2),resultSet.getString(6),resultSet.getString(5),resultSet.getInt(4));
                }
                else if(resultSet.getInt(4)==1){//Student
                    currentUser= new Student(resultSet.getString(3),resultSet.getString(2),resultSet.getString(6),resultSet.getString(5),resultSet.getInt(4),resultSet.getString(7));

                }

                else{//Partner
                    throw new Exception("Type de compte pas encore implementé");
                }

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return currentUser;
    }
}
