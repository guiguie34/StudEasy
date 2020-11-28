package com.github.studeasy.dao.userDAO;

import com.github.studeasy.dao.DAO;

import java.sql.*;
import java.sql.SQLException;

public class MySQLUserDAO extends DAO implements UserDAO{

    @Override
    public void searchUser(String email, String password) {
        int id;
        String firstName;
        String lastName;
        int role;
        String pwd;
        String emailAdress;
        String pseudo;
        String company;

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = db.prepareStatement("SELECT * FROM user WHERE user.emailAddress = ? AND user.password = ?");
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt(1);
                firstName = resultSet.getString(2);
                lastName = resultSet.getString(3);
                role = resultSet.getInt(4);
                pwd = resultSet.getString(5);
                emailAdress = resultSet.getString(6);
                pseudo = resultSet.getString(7);
                company = resultSet.getString(8);
                System.out.println("COUCOU LES LOULOUS VOUS VENEZ DE LOAD "+firstName+" "+lastName+" son role est "+role+" et son mdp est "+pwd+" etc... pas très sécurisé tout ça");
                System.out.println("C'est là où on créé le user");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }
}
