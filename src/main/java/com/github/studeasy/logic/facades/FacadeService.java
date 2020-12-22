package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.serviceDAO.ServiceDAO;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;

import java.util.ArrayList;
import java.util.Date;

/**
 * The Facade Service for the ServiceDAO
 * It contains methods service related
 */
public class FacadeService {

    /**
     * Singleton of the facade service
     */
    private static FacadeService facadeService = null;

    /**
     * The DAO connected to the database
     */
    private final ServiceDAO DAO;

    /**
     * Constructor of singleton FacadeCategory
     * Instantiate the factory
     */
    private FacadeService() {
        // We retrieve the ServiceDAO
        this.DAO = ServiceDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeService
     * @return the instance of FacadeService
     */
    public static FacadeService getInstance(){
        if(facadeService == null){
            facadeService = new FacadeService();
        }
        return facadeService;
    }

    /**
     * Retrieve the services of the current user
     * @return the services of the userin the session
     */
    public ArrayList<Service> getMyServices(){
        // We retrieve the current user
        Session sessionUser = Session.getInstance();
        User currentUser = sessionUser.getCurrentUser();
        // We ask the DAO to retrieve the services of the user
        return DAO.getMyServices(currentUser);
    }

    /**
     * Function used to check if we can add the service, then add it
     * @param titleS the title of the new service
     * @param descriptionS the description of the new service
     * @param category the category associated to the new service
     * @param cost the cost of the new service
     * @param typeS the type of the service
     * @throws BadInformationException if the service is not conform
     */
    public void submitService(String titleS, String descriptionS, CategoryTag category, int cost, int typeS) throws BadInformationException {
        // We check if the title and the description fit
        if((titleS.length() >= 2 && titleS.length() <= 25) && descriptionS.length() <= 500){
            // We retrieve the user creating the service
            Session sessionUser = Session.getInstance();
            User currentUser = sessionUser.getCurrentUser();
            Date creationDate = new Date();
            // We ask the DAO to create the service
            DAO.submitService(titleS,descriptionS,category,cost,typeS,creationDate,currentUser);
        }
        else{
            // We tell the user what's wrong
            throw new BadInformationException("The title and description do not have a correct length");
        }
    }
}