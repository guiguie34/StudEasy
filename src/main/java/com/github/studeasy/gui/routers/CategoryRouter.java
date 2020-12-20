package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.categoryTag.AddUpdateCategoryController;
import com.github.studeasy.logic.common.CategoryTag;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Router for the categories related routes
 */
public class CategoryRouter extends AbstractRouter{

    /**
     * Singleton of the category router
     */
    private static CategoryRouter categoryRouter = null;

    /**
     * Path to categories management view
     */
    public final static String MANAGE_CATEGORY_FXML_PATH = "views/category/manage_category.fxml";
    /**
     * Path to add or update a category view
     */
    public final static String ADD_UPDATE_CATEGORY_FXML_PATH = "views/category/add_update_category.fxml";    /**

    /**
     * Calls the parent constructor, getting the
     * instance of the session
     */
    private CategoryRouter(){
        super();
    }

    /**
     * Used to return the unique instance of the CategoryRouter
     * @return the categoryRouter
     */
    public static CategoryRouter getInstance(){
        if(categoryRouter == null){
            categoryRouter = new CategoryRouter();
        }
        return categoryRouter;
    }

    /**
     * Load the view and display the good label and buttons for adding or
     * updating a category
     * @param pathFXML the path to the fxml file
     * @param event the action trigerring the change of view
     * @param addUpdate 0 if we add, 1 if we update
     * @param categoryTagToUpdate the categoryTag we want to update or null
     * @throws IOException if an error occurs
     */
    public void addOrUpdateCategory(String pathFXML, ActionEvent event, int addUpdate, CategoryTag categoryTagToUpdate) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with addUpdate telling if we add or update
        AddUpdateCategoryController addUpdateCategoryController = new AddUpdateCategoryController(addUpdate,categoryTagToUpdate);
        // We link this controller with the FXML
        loader.setController(addUpdateCategoryController);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event,root);
    }
}