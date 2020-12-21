package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.serviceDAO.ServiceDAO;

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
}
