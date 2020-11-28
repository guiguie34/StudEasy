package com.github.studeasy.dao.db;

import java.sql.*;
import javax.swing.*;

public class MySQLConnectionUtil implements ConnectionUtilI{
    private Connection db = null;

    private MySQLConnectionUtil()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://database-1.c4y60ykreptw.eu-west-3.rds.amazonaws.com:3306/";
            String user = "admin";
            String passwd = "studeasy";
            String dbName = "studeasy";
            this.db = DriverManager.getConnection(url+dbName, user, passwd);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private static class createConn{
        public static final MySQLConnectionUtil instance= new MySQLConnectionUtil();
    }

    public static MySQLConnectionUtil getInstance(){
        return createConn.instance;
    }

    public Connection getDb(){
        return db;
    };

    /*
    public static void main(String[] args){
        ConnectionUtilI connection = MySQLConnectionUtil.getInstance();
        Connection db = connection.getDb();
        try {
            PreparedStatement preparedStatement = db.prepareStatement("SELECT * FROM user ");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
     */
}