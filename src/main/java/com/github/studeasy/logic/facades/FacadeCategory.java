package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.categoryDAO.CategoryDAO;

/**
 * The category facade
 * It contains the methods category related
 */
public class FacadeCategory {

    /**
     * Singleton of the facade category
     */
    private static FacadeCategory facadeCategory = null;

    /**
     * The DAO connected to the database
     */
    private final CategoryDAO DAO;

    /**
     * Constructor of singleton FacadeCategory
     * Instantiate the factory
     */
    private FacadeCategory() {
        // We retrieve the FacadeDAO
        this.DAO = CategoryDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeCategory
     * @return the instance of FacadeUser
     */
    public static FacadeCategory getInstance(){
        if(facadeCategory == null){
            facadeCategory = new FacadeCategory();
        }
        return facadeCategory;
    }

    /**
     * TODO delete / add / update categories
     */
}
