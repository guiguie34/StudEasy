package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.jobDAO.JobDAO;
import com.github.studeasy.logic.common.Job;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import com.github.studeasy.logic.utils.regexUtils;

import java.time.LocalDate;
import java.util.ArrayList;


/**
 * The Facade User for the JobDAO
 * It contains methods that allow to create,update,delete,... Job
 */
public class FacadeJob {

    /**
     * Singleton of the facade user
     */
    private static FacadeJob facadeJob = null;

    /**
     * The DAO connected to the database
     */
    private final JobDAO DAO;

    /**
     * Constructor of singleton FacadeUser
     * Instantiate the factory
     */
    private FacadeJob() {
        // We retrieve the UserDao
        this.DAO = JobDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeJob
     * @return the instance of FacadeJob
     */
    public static FacadeJob getInstance(){
        if(facadeJob == null){
            facadeJob = new FacadeJob();
        }
        return facadeJob;
    }

    /**
     * Get information from session and call DAO to add Job to the DB
     * @param title
     * @param location
     * @param role
     * @param duration
     * @param mail
     * @param phone
     * @param localDate
     * @param description
     * @throws Exception
     */
    public void addJob(String title, String location, String role, String duration, String mail, String phone, LocalDate localDate, String description) throws Exception {
        Session sessionUser = Session.getInstance();
        User currentUser = sessionUser.getCurrentUser();
        if(regexUtils.matches_mail(mail)) {
            if(regexUtils.matches_phone(phone)) {
                DAO.addJob(title, location, role, duration, mail, phone, localDate, description, currentUser);
                FacadeNotification facadeNotification = FacadeNotification.getInstance();
                String titleN = "Job pending!";
                String desc = "Your job offer: "+title+" is now waiting for an administrator validation.\n";
                try {
                    facadeNotification.createNotification(currentUser.getIdUser(),titleN,desc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                throw new BadInformationException("Phone number is not valid, please retry");
            }
        }
        else{
            throw new BadInformationException("Mail not valid, please retry");
        }
    }

    /**
     * Return the pending job
     * @return arraylist of pending jobs
     * @throws Exception if an error occurs
     */
    public ArrayList<Job> getPendingJob() throws Exception {
        return DAO.getPendingJob();
    }

    /**
     * Submit the admin choice for the selected job
     * @param job Selected job
     * @param choice choice
     * @throws Exception if an error occurs
     */
    public void choiceForJob(Object job,int choice) throws Exception{
        DAO.choiceForJob((Job)job,choice);
        String title = "";
        String desc = "";
        switch(choice){
            case 1:
                // We reject
                title = "Job rejected!";
                desc = "Your job: "+((Job) job).getTitle()+" has been rejected.";
                break;
            case 2:
                // We accept
                title = "Job online!";
                desc = "Your job: "+((Job) job).getTitle()+" has been validated and can be seen online !";
                break;
        }

        try {
            FacadeNotification facadeNotification = FacadeNotification.getInstance();
            facadeNotification.createNotification(((Job) job).getOwner().getIdUser(),title,desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Return all jobs
     * @return arraylist of all jobs
     * @throws Exception if an error occurs
     */
    public ArrayList<Job> getAllJobs() throws Exception {
        return DAO.getAllJobs();
    }

    /**
     * Return all jobs from one user
     * @return arraylist of all jobs
     * @throws Exception if an error occurs
     */
    public ArrayList<Job> getMyJobs(Object user) throws Exception {
        return DAO.getMyJobs((User) user);
    }

    /**
     * Call DAO to delete Job
     * @param job Job to delete
     */
    public void deleteJob(Object job) throws Exception {
        DAO.deleteJob((Job)job);
        FacadeNotification facadeNotification = FacadeNotification.getInstance();
        String title = "Job deleted!";
        String desc = "Your job offer: "+((Job) job).getTitle()+" has been deleted.";
        try {
            facadeNotification.createNotification(((Job) job).getOwner().getIdUser(),title,desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateJob(String title, String location, String role, String duration, String mail, String phone, LocalDate localDate, String description, int idJob) throws Exception{
        if(regexUtils.matches_mail(mail)) {
            if(regexUtils.matches_phone(phone)) {
                DAO.updateJob(title, location, role, duration, mail, phone, localDate, description, idJob);
            }
            else{
                throw new BadInformationException("Phone number is not valid, please retry");
            }
        }
        else{
            throw new BadInformationException("Mail not valid, please retry");
        }
    }
}
