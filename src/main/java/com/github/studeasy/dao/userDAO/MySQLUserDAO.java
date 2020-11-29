package com.github.studeasy.dao.userDAO;

import com.github.studeasy.dao.DAO;
import com.github.studeasy.dao.exceptions.BadPasswordException;
import com.github.studeasy.logic.common.Student;
import com.github.studeasy.logic.common.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUserDAO extends DAO implements UserDAO{
    @Override
    public User loginUser(String email, String password) throws Exception, BadPasswordException {
        User currentUser=null;
        try {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String request = "SELECT * FROM user WHERE emailAddress = ? and password = ?";
            preparedStatement = db.prepareStatement(request);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {

                throw new BadPasswordException("Mauvaise combinaison");
            } else {
                System.out.println("Good id");
                if(resultSet.getInt(4)==0){//Admin
                    System.out.println("Admin");
                    currentUser = new User(resultSet.getString(3),resultSet.getString(2),resultSet.getString(6),resultSet.getString(5),resultSet.getInt(4));
                }
                else if(resultSet.getInt(4)==1){//Student
                    System.out.println("Student");
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
