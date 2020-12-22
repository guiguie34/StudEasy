package com.github.studeasy.dao.categoryDAO;

import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The Category DAO using a MySQL database
 * Contains all the methods accessing category related data
 */
public class MySQLCategoryDAO extends CategoryDAO{

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLCategoryDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }

    /**
     * Retrieve all the categories in the database
     * @return the categories
     */
    public ArrayList<CategoryTag> seeAllCategories(){
        ArrayList<CategoryTag> categories = new ArrayList<>();
        try {
            // We prepare the SQL request to retrieve a category
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM categorytag";
            preparedStatement = DB.prepareStatement(request);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We retrieve all the existing categories
            while (resultSet.next()) {
                // We create the category
                CategoryTag category = new CategoryTag(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3));
                // And put it with the others
                categories.add(category);
            }
        }
        // Error with the database
        catch(SQLException err){
            err.printStackTrace();
        }
        return categories;
    }

    /**
     * Function used to delete a category from the database
     * @param categoryToDelete the category to delete
     */
    public void deleteCategory(CategoryTag categoryToDelete){
        // We prepare the SQL request to delete a category tag
        PreparedStatement preparedStatement;
        String request = "DELETE FROM categorytag WHERE idCategory = ?";
        try {
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, categoryToDelete.getIdCat());
            // We execute the query
            preparedStatement.executeUpdate();
        }
        // Error with the database
        catch (SQLException err) {
            err.printStackTrace();
        }
    }

    /**
     * Function used to add a category in the database
     * @param nameCat the name of the category
     * @param descriptionCat the description of the category
     */
    public void submitAddCategory(String nameCat, String descriptionCat) {
        // We prepare the SQL request to insert a category tag
        PreparedStatement preparedStatement;
        String request = "INSERT INTO categorytag (nameCategory,descriptionCategory) VALUES  (?,?)";
        try {
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, nameCat);
            preparedStatement.setString(2, descriptionCat);
            // We execute the query
            preparedStatement.executeUpdate();
        }
        // Error with the database
        catch (SQLException err) {
            err.printStackTrace();
        }
    }

    /**
     * Function used to update a category in the database
     * @param nameCat the name of the category
     * @param descriptionCat the description of the category
     * @param categoryToUpdate the category to update
     */
    public void submitUpdateCategory(String nameCat, String descriptionCat, CategoryTag categoryToUpdate){
        // We prepare the SQL request to insert a category tag
        PreparedStatement preparedStatement;
        String request = "UPDATE categorytag SET nameCategory = ?, descriptionCategory = ? WHERE idCategory = ?";
        try {
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, nameCat);
            preparedStatement.setString(2, descriptionCat);
            preparedStatement.setInt(3, categoryToUpdate.getIdCat());

            // We execute the query
            preparedStatement.executeUpdate();
        }
        // Error with the database
        catch (SQLException err) {
            err.printStackTrace();
        }
    }

    /**
     * Function used to retrieve a category with the name given
     * @param nameCat the name of the category wanted, or null if it doesn't exist
     * @return the categoryTag corresponding, or null if there isn't
     */
    public CategoryTag searchCategory(String nameCat){
        CategoryTag existingCat = null;
        try {
            // We prepare the SQL request to retrieve a category
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM categorytag WHERE nameCategory = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, nameCat);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We check if the query retrieved a category
            if (resultSet.next()) {
                // We create the corresponding category
                existingCat = new CategoryTag(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3));
            }
        }
        // Error with the database
        catch(SQLException err){
            err.printStackTrace();
        }
        return existingCat;
    }
}