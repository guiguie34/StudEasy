package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.commandOfServiceDAO.CommandOfServiceDAO;
import com.github.studeasy.dao.feedbackDAO.FeedbackDAO;
import com.github.studeasy.logic.common.CommandOfService;

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

    /***
     *
     * @param c
     */
    public void acceptTransaction(CommandOfService c){

    }

}
