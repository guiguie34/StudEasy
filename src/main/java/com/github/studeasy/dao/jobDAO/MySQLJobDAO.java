package com.github.studeasy.dao.jobDAO;

import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * The job DAO using a MySQL database
 * Contains all the methods accessing user related data
 */
public class MySQLJobDAO extends JobDAO{

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLJobDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }

    /**
     * Add Job to the DB with initial pending state
     * @param title
     * @param location
     * @param role
     * @param duration
     * @param mail
     * @param phone
     * @param localDate
     * @param description
     * @param currentUser
     * @throws Exception
     */
    @Override
    public void addJob(String title, String location, String role, String duration, String mail, String phone, LocalDate localDate, String description, Object currentUser) throws Exception {

        try {
            // We prepare the SQL request to retrieve a user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            int resultSet;
            String request = "INSERT INTO job(ownerJob,titleJob,localisationJob,roleJob,startJob,durationJob,descriptionJob,contactMailJob,contactPhoneJob) VALUES(?,?,?,?,?,?,?,?,?)";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, ((User)currentUser).getIdUser());
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, role);
            preparedStatement.setString(5, localDate.toString());
            preparedStatement.setString(6, duration);
            preparedStatement.setString(7, description);
            preparedStatement.setString(8, mail);
            preparedStatement.setString(9, phone);
            // We execute the query
            resultSet = preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            throw e;
        }
    }
}
