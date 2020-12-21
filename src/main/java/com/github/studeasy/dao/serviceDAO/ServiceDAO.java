package com.github.studeasy.dao.serviceDAO;

import com.github.studeasy.logic.factory.Factory;

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
}
