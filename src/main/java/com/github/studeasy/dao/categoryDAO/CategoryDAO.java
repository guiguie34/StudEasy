package com.github.studeasy.dao.categoryDAO;

import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.factory.Factory;

import java.util.ArrayList;

/**
 * Abstract class for the Category DAO
 * Contains the methods needed by the Category DAO
 */
public abstract class CategoryDAO {
    /**
     * The singleton of the CategoryDAO
     */
    private static CategoryDAO categoryDAO = null;

    /**
     * Static method which returns the instance of the CategoryDAO,
     * or ask the factory to create one
     * @return the instance of MySQLUserDAO
     */
    public static CategoryDAO getInstance(){
        if (categoryDAO == null){
            Factory factory = Factory.getInstance();
            categoryDAO = factory.createCategoryDAO();
        }
        return categoryDAO;
    }

    /**
     * Retrieve all the categories in the database
     * @return the categories
     */
    public abstract ArrayList<CategoryTag> seeAllCategories();

    /**
     * Function used to delete a category from the database
     * @param categoryToDelete the category to delete
     */
    public abstract void deleteCategory(CategoryTag categoryToDelete);

    /**
     * Function used to add a category in the database
     * @param nameCat the name of the category
     * @param descriptionCat the description of the category
     */
    public abstract void submitAddCategory(String nameCat, String descriptionCat);

    /**
     * Function used to retrieve a category with the name given
     * @param nameCat the name of the category wanted
     * @return the categoryTag corresponding, or null if there isn't
     */
    public abstract CategoryTag searchCategory(String nameCat);
}
