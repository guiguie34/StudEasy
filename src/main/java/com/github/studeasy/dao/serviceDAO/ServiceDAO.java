package com.github.studeasy.dao.serviceDAO;

import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.util.ArrayList;

/**
 * Abstract class for the Service DAO
 * Contains the methods needed by the Service DAO
 */
public abstract class ServiceDAO {

    /**
     * The singleton of the ServiceDAO
     */
    private static ServiceDAO serviceDAO = null;

    /**
     * Static method which returns the instance of the ServiceDAO,
     * or ask the factory to create one
     * @return the instance of MySQLServiceDAO
     */
    public static ServiceDAO getInstance(){
        if (serviceDAO == null){
            Factory factory = Factory.getInstance();
            serviceDAO = factory.createServiceDAO();
        }
        return serviceDAO;
    }

    /**
     * Retrieve the services of the user
     * @param currentUser the user wanting to see his services
     * @return the services of the user
     */
    public abstract ArrayList<Service> getMyServices(User currentUser);

    /**
     * Retrieve all the pending services
     * @return the pending services
     */
    public abstract ArrayList<Service> getPendingServices();

    /**
     * Retrieve all the services
     * @return the services
     */
    public abstract ArrayList<Service> getOnlineServices();

    /**
     * Set to default the category of the service
     */
    public abstract void setDefaultCategory();

    /**
     * Delete the service
     * @param service the service to delete
     */
    public abstract void deleteService(Service service);

    /**
     * Update the service with this category
     * @param categoryS the new category
     * @param service the service to update
     */
    public abstract void updateCategoryService(CategoryTag categoryS, Service service);

    /**
     * Validate the service
     * @param service the service to validate
     */
    public abstract void validateService(Service service);

    /**
     * Create a service with those information
     * @param titleS the title of the new service
     * @param descriptionS the description of the new service
     * @param category the category associated to the new service
     * @param cost the cost of the new service
     * @param typeS the type of the service
     * @param user the user creating the service
     */
    public abstract void submitService(String titleS, String descriptionS, CategoryTag category,
                                       int cost, int typeS, User user);


}
