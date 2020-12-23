package com.github.studeasy.dao.db;

import com.github.studeasy.logic.utils.PropertiesEnv;

import java.sql.*;
import java.util.Properties;
import javax.swing.*;

/**
 * Class singleton, creating a connection to our MySQL database
 */
public class MySQLConnectionUtil{

    /**
     * Singleton of the MySQLConnectionUtil
     */
    private static MySQLConnectionUtil mySQLConnectionUtil = null;

    /**
     * The connection to the database
     */
    private Connection db = null;

    /**
     * Constructor of the class, creates the connection to the database
     */
    private MySQLConnectionUtil()
    {
        Properties p = PropertiesEnv.getDatabaseProperties();
        try
        {
            // We set the information of the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = p.getProperty("URL");
            String user = p.getProperty("USER");
            String passwd = p.getProperty("PWD");
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
     * Retrieve the singleton of the class
     * @return the singleton of the class
     */
    public static MySQLConnectionUtil getInstance(){
        if(mySQLConnectionUtil == null){
            mySQLConnectionUtil = new MySQLConnectionUtil();
        }
        return mySQLConnectionUtil;
    }

    /**
     * Retrieve the connection to the database
     * @return the connection to the database
     */
    public Connection getDb(){
        return db;
    }
}