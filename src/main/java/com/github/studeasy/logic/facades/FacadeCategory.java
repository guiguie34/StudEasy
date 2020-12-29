package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.categoryDAO.CategoryDAO;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;

import java.util.ArrayList;

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
     * @return the instance of FacadeCategory
     */
    public static FacadeCategory getInstance(){
        if(facadeCategory == null){
            facadeCategory = new FacadeCategory();
        }
        return facadeCategory;
    }

    /**
     * We retrieve all the categories in the database
     * @return all the categories of the database
     */
    public ArrayList<CategoryTag> seeAllCategories(){
        // We ask the DAO to retrieve the categories
        return DAO.seeAllCategories();
    }

    /**
     * Function asking the DAO to delete a category
     * @param categoryToDelete the category to delete
     */
    public void deleteCategory(CategoryTag categoryToDelete){
        // We ask the DAO to delete the category
        DAO.deleteCategory(categoryToDelete);
    }

    /**
     * Check if a category is good to be updated or created
     * @param nameCat the name of the category
     * @param descriptionCat the description of the category
     * @return true if it's ok
     * @throws BadInformationException the error indicating what's wrong
     */
    private boolean changesCategoryAllowed(String nameCat, String descriptionCat) throws BadInformationException{
        // We check the values are not empty
        if(!nameCat.isEmpty() && !descriptionCat.isEmpty()){
            // It's not empty, we now check if it's a valid name and description
            if((nameCat.length() >= 2 && nameCat.length() <= 25) && (descriptionCat.length() <= 50)){
                return true;
            }
            // The name doesn't have a fitted size
            else{
                throw new BadInformationException("The name and description do not have a correct length");
            }
        }
        // One of the value is empty
        else {
            throw new BadInformationException("All fields are required");
        }
    }

    /**
     * Function used to add a category
     * @param nameCat the name of the category
     * @param descriptionCat the description of the category
     * @throws BadInformationException occurs when bad information are provided
     */
    public void submitAddCategory(String nameCat, String descriptionCat) throws BadInformationException{
        // We check if we can add the category
        if(changesCategoryAllowed(nameCat,descriptionCat)){
            // We search for a category with the same name
            CategoryTag existingCat = DAO.searchCategory(nameCat);
            // If it doesn't exist, we can create the new category
            if(existingCat == null){
                // We create the category
                DAO.submitAddCategory(nameCat,descriptionCat);
            }
            // Already exists
            else {
                throw new BadInformationException("A category with this name already exists");
            }
        }
    }

    /**
     * Function used to update a category
     * @param nameCat the name of the category
     * @param descriptionCat the description of the category
     * @param categoryToUpdate the category to update
     * @throws BadInformationException occurs when bad information are provided
     */
    public void submitUpdateCategory(String nameCat, String descriptionCat, CategoryTag categoryToUpdate) throws BadInformationException{
        // We check if we can update with those name and description
        if(changesCategoryAllowed(nameCat,descriptionCat)){
            // We search if there is a category with the same name
            CategoryTag existingCat = DAO.searchCategory(nameCat);
            // If it doesn't exist, or if it is the same name as the updated category
            if(existingCat == null || existingCat.getName().equals(categoryToUpdate.getName())){
                // We update the category
                DAO.submitUpdateCategory(nameCat, descriptionCat, categoryToUpdate);
            }
            // Already exists
            else {
                throw new BadInformationException("A category with this name already exists");
            }
        }
    }
}
