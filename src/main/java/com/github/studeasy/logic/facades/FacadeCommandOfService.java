package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.commandOfServiceDAO.CommandOfServiceDAO;
import com.github.studeasy.dao.feedbackDAO.FeedbackDAO;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The Facade User for the CommandOfServiceDAO
 * It contains methods that allow to create,update,delete a command
 */
public class FacadeCommandOfService {

    /**
     * Singleton of the facade CommandOfServie
     */
    private static FacadeCommandOfService facadeCommandOfService= null;

    /**
     * The DAO connected to the database
     */
    private final CommandOfServiceDAO DAO;


    /**
     * Constructor of singleton FacadeCommandOfService
     * Instantiate the factory
     */
    private FacadeCommandOfService() {
        // We retrieve the UserDao
        this.DAO = CommandOfServiceDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeCommandOfService
     * @return the instance of FacadeCommandOfService
     */
    public static FacadeCommandOfService getInstance(){
        if(facadeCommandOfService == null){
            facadeCommandOfService = new FacadeCommandOfService();
        }
        return facadeCommandOfService;
    }

    /**
     * Retrieve the command of the current user
     * @return the command of the user
     */
    public ArrayList<CommandOfService> getMyCommand() throws Exception {
        // We retrieve the current user
        Session sessionUser = Session.getInstance();
        User currentUser = sessionUser.getCurrentUser();
        // We ask the DAO to retrieve the services of the user
        return DAO.getServiceBought(currentUser);
    }

    public ArrayList<CommandOfService> getMyCommandPending() throws Exception {
        // We retrieve the current user
        Session sessionUser = Session.getInstance();
        User currentUser = sessionUser.getCurrentUser();
        // We ask the DAO to retrieve the services of the user
        return DAO.getMyServicePending(currentUser);
    }

    /***
     * Methode that allows to accept a command by asking DAO
     * @param c
     * @throws Exception
     */
    public void acceptTransaction(CommandOfService c) throws Exception {
        DAO.acceptTransaction(c);
    }

    /***
     *Methode that allows to delete a command by asking DAO
     * @param c
     * @throws Exception
     */
    public void declineTransaction(CommandOfService c) throws Exception {
        DAO.declineTransaction(c);
    }

    /***
     * Methode allows to buy a service and save the record
     * @param s
     * @throws Exception
     */
    public void buyorapplyService(Service s, User u) throws Exception{
        DAO.applyorbuyForService(s,u);
    }


    /***
     * Methode allows to add a feedback to a service
     * @param c
     * @throws Exception
     */
    public void addFeedback(CommandOfService c) throws Exception {
        DAO.addFeedback(c);
    }




}
