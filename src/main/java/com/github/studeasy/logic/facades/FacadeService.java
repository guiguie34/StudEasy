package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.serviceDAO.ServiceDAO;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;

import java.util.ArrayList;

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
     * Retrieve all the pending services
     * @return the pending services
     */
    public ArrayList<Service> getPendingServices() {
        // We ask the DAO to retrieve the pending services
        return DAO.getPendingServices();
    }

    /**
     * Retrieve all the services
     * @return the services
     */
    public ArrayList<Service> getOnlineServices() {
        // We ask the DAO to retrieve all the services
        return DAO.getOnlineServices();
    }

    /**
     * Delete the service
     * @param service the service to delete
     */
    public void deleteService(Service service) {
        // We ask the DAO to delete it
        this.DAO.deleteService(service);
    }

    /**
     * Update the service with this category
     * @param categoryS the new category
     * @param service the service to update
     */
    public void updateCategoryService(CategoryTag categoryS, Service service) {
        // We ask the DAO to update the service
        this.DAO.updateCategoryService(categoryS,service);
    }

    /**
     * Validate the service
     * @param service the service to validate
     */
    public void validateService(Service service){
        // We ask the DAO to validate the service
        this.DAO.validateService(service);
        // We send a notification to the user
        FacadeNotification facadeNotification = FacadeNotification.getInstance();
        String title = "Service online !";
        String desc = "Congratulations,\nYour service : "+service.getTitle()+" has been validated by an administrator !\n"
                + "You can now find it online with all the services.";
        try {
            facadeNotification.createNotification(service.getOwner().getIdUser(),title,desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            // We ask the DAO to create the service
            DAO.submitService(titleS,descriptionS,category,cost,typeS,currentUser);
        }
        else{
            // We tell the user what's wrong
            throw new BadInformationException("The title and description do not have a correct length");
        }
    }
}