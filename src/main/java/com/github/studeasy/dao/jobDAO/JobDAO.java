package com.github.studeasy.dao.jobDAO;

import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Job;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Interface for the Job DAO
 * Contains the methods needed by the Job DAO
 */
public abstract class JobDAO {

    /**
     * The singleton of the JobDAO
     */
    private static JobDAO jDAO = null;

    /**
     * Static method which returns the instance of the UserDAO,
     * or ask the factory to create one
     * @return the instance of MySQLUserDAO
     */
    public static JobDAO getInstance(){
        if (jDAO == null){
            Factory factory = Factory.getInstance();
            jDAO = factory.createJobDAO();
        }
        return jDAO;
    }

    /**
     * Add/update Job to the DB
     * @throws Exception
     */
    public abstract void addJob(String title, String location, String role, String duration, String mail, String phone, LocalDate localDate, String description, Object currentUser) throws Exception;

    /**
     * Get all the pending job
     * @return
     */
    public abstract ArrayList<Job> getPendingJob() throws Exception;


    /**
     * Submit admin choice for selected job
     * @param job
     * @param choice
     * @throws Exception
     */
    public abstract void choiceForJob(Object job,int choice) throws Exception;


    /**
     * Get all jobs from the DB
     * @return Arraylist of all jobs
     * @throws Exception if an error occurs
     */
    public abstract ArrayList<Job> getAllJobs() throws Exception;

    /**
     * Get all jobs from one user
     * @param user the user
     * @return arraylist of user's job
     * @throws Exception if an error occurs
     */
    public abstract ArrayList<Job> getMyJobs(Object user) throws Exception;

    /**
     * Delete job from DB
     * @param job Job to delete
     * @throws Exception if an error occurs
     */
    public abstract void deleteJob(Object job) throws Exception;

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
    public abstract void updateJob(String title, String location, String role, String duration, String mail, String phone, LocalDate localDate, String description, int idJob) throws Exception;
}
