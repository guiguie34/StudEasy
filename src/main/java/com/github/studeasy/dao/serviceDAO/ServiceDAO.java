package com.github.studeasy.dao.serviceDAO;

import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.factory.Factory;

import java.util.Date;

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
     * Create a service with those information
     * @param titleS the title of the new service
     * @param descriptionS the description of the new service
     * @param category the category associated to the new service
     * @param cost the cost of the new service
     * @param typeS the type of the service
     * @param creationDate the date of creation
     */
    public abstract void submitService(String titleS, String descriptionS, CategoryTag category,
                                       int cost, int typeS, Date creationDate);
}
