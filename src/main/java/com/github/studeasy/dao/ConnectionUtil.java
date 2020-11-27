package com.github.studeasy.dao;

import java.sql.*;
import javax.swing.*;


public class ConnectionUtil {
    Connection conn = null;
    public static Connection connectdb()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://database-1.c4y60ykreptw.eu-west-3.rds.amazonaws.com:3306/";
            String user = "admin";
            String passwd = "studeasy";
            String dbName = "studeasy";
            Connection conn = DriverManager.getConnection(url+dbName, user, passwd);
            return conn;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    /*public static void main(String[] args){
        Connection con= ConnectionUtil.connectdb();

    }*/
}