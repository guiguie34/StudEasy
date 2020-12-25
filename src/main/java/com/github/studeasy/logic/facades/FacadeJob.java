package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.jobDAO.JobDAO;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import com.github.studeasy.logic.utils.regexUtils;

import java.time.LocalDate;


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
