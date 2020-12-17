package com.github.studeasy.gui.controller.categoryTag;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CategoryRouter;
import com.github.studeasy.logic.facades.FacadeCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the management of the categories
 */
public class CategoryManagementController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     /**
     * The facade used by the controller
     */
    private final FacadeCategory FACADE;

    /**
     * The field to search a category
     */
    @FXML
    private TextField searchCategoryTF;

    /**
     * Instantiate the attributes with
     * a router and a facade used for categories
     */
    public CategoryManagementController(){
        this.ROUTER = CategoryRouter.getInstance();
        this.FACADE = FacadeCategory.getInstance();
    }

    /**
     * Triggered when the admin wants to go to the add/update a category page
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void addNewCategory(ActionEvent event) throws IOException {
        ((CategoryRouter)ROUTER).addOrUpdateCategory(CategoryRouter.ADD_UPDATE_CATEGORY_FXML_PATH,event,0);
    }

    /**
     * TODO do a real search
     * Triggered when the admin wants to search a particular category
     * @param event the event triggered
     */
    public void searchCategory(ActionEvent event) {
        String categorySearched = searchCategoryTF.getText();
        System.out.println("SEARCHING : "+categorySearched);
    }

    /**
     * TODO delete / update buttons per category
      */

    /**
     * TODO cancel button
     */

    /**
     * TODO display all the categories
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}