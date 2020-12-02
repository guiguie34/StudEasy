package com.github.studeasy.dao.db;

import java.sql.*;
import javax.swing.*;

/**
 * Class singleton, creating a connection to our MySQL database
 */
public class MySQLConnectionUtil{

    /**
     * The connection to the database
     */
    private Connection db = null;

    /**
     * Constructor of the class, creates the connection to the database
     */
    private MySQLConnectionUtil()
    {
        try
        {
            // We set the information of the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://database-1.c4y60ykreptw.eu-west-3.rds.amazonaws.com:3306/";
            String user = "admin";
            String passwd = "studeasy";
            String dbName = "studeasy";
            // We try to connect to the database using those information
            this.db = DriverManager.getConnection(url+dbName, user, passwd);
        }
        catch(Exception e)
        {
            // An error occured, we tell the user
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Lazy holder, create the unique connection to the database
     */
    private static class createConn{
        static final MySQLConnectionUtil instance= new MySQLConnectionUtil();
    }

    /**
     * Allows to retrieve the singleton of the class
     * @return the singleton of the class
     */
    public static MySQLConnectionUtil getInstance(){
        return createConn.instance;
    }

    /**
     * Allows to retrieve the connection to the database
     * @return the connection to the database
     */
    public Connection getDb(){
        return db;
    }
}