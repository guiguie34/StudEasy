package com.github.studeasy.dao.jobDAO;

import com.github.studeasy.dao.userDAO.MySQLUserDAO;
import com.github.studeasy.logic.common.Job;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Job> getPendingJob() throws Exception{
        List<Job> jobs = new ArrayList<Job>();
        MySQLUserDAO searchUser = new MySQLUserDAO();
        try {
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM job WHERE status= 'pending'";
            preparedStatement = DB.prepareStatement(request);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                jobs.add(new Job(resultSet.getInt(1),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10), searchUser.searchUserById(resultSet.getInt(2)),resultSet.getString(11)));
            }
        } catch (Exception e) {
            throw e;
        }
        return ((ArrayList<Job>) jobs);
    }


    public void choiceForJob(Object job,int choice) throws Exception{
        try {
            Job currentJob = (Job)job;
            String status = "";
            if(choice ==1){
                status = "refused";
            }
            if(choice ==2){
                status = "validated";
            }
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            int resultSet;
            String request = "UPDATE job SET status=? WHERE idJob = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, currentJob.getIdJob());

            resultSet = preparedStatement.executeUpdate();
        }
        catch (Exception e){
            throw e;
        }
    }


    public ArrayList<Job> getAllJobs() throws Exception{
        List<Job> jobs = new ArrayList<Job>();
        MySQLUserDAO searchUser = new MySQLUserDAO();
        try {
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM job WHERE status= 'validated'";
            preparedStatement = DB.prepareStatement(request);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                jobs.add(new Job(resultSet.getInt(1),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10), searchUser.searchUserById(resultSet.getInt(2)),resultSet.getString(11)));
            }
        } catch (Exception e) {
            throw e;
        }
        return ((ArrayList<Job>) jobs);
    }


    public ArrayList<Job> getMyJobs(Object user) throws Exception{
        List<Job> jobs = new ArrayList<Job>();
        MySQLUserDAO searchUser = new MySQLUserDAO();
        try {
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM job WHERE ownerJob= ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, ((User)user).getIdUser());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                jobs.add(new Job(resultSet.getInt(1),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10), searchUser.searchUserById(resultSet.getInt(2)),resultSet.getString(11)));
            }
        } catch (Exception e) {
            throw e;
        }
        return ((ArrayList<Job>) jobs);
    }

    public void deleteJob(Object job) throws Exception {
        try {
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            int resultSet;
            String request = "DELETE  FROM job WHERE idJob=?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, ((Job) job).getIdJob());
            resultSet = preparedStatement.executeUpdate();
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * Update job
     * @param title
     * @param location
     * @param role
     * @param duration
     * @param mail
     * @param phone
     * @param localDate
     * @param description
     * @param idJob
     * @throws Exception
     */
    @Override
    public void updateJob(String title, String location, String role, String duration, String mail, String phone, LocalDate localDate, String description, int idJob) throws Exception{
        try {

            PreparedStatement preparedStatement;
            int resultSet;
            String request = "UPDATE  job SET titleJob =?,localisationJob=?,roleJob=?,startJob=?,durationJob=?,descriptionJob=?,contactMailJob=?,contactPhoneJob=?,status=? WHERE idJob =?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, location);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, localDate.toString());
            preparedStatement.setString(5, duration);
            preparedStatement.setString(6, description);
            preparedStatement.setString(7, mail);
            preparedStatement.setString(8, phone);
            preparedStatement.setString(9, "pending");
            preparedStatement.setInt(10,idJob);
            // We execute the query
            resultSet = preparedStatement.executeUpdate();

        }
        catch(SQLException e){
            throw e;
        }
    }

}
