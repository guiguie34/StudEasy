package com.github.studeasy.gui.controller.categoryTag;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CategoryRouter;
import com.github.studeasy.logic.facades.FacadeCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller used to add or update a category
 */
public class AddUpdateCategoryController implements Initializable {

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
     * Label printing add or update a category
     */
    @FXML
    private Label addUpdateL;

    /**
     * The name of the category to add/update
     */
    @FXML
    private TextField nameCatTF;

    /**
     * The description of the category to add/update
     */
    @FXML
    private TextArea descriptionCatTF;

    /**
     * Button add or update
     */
    @FXML
    private Button addUpdateB;

    /**
     * Indicates if we want to add or update
     * 0 add,
     * 1 update
     */
    private int addUpdate;

    /**
     * Instantiate the attributes with
     * a router and a facade used for categories
     * @param addUpdate 0 if we add, 1 if we update
     */
    public AddUpdateCategoryController(int addUpdate){
        this.ROUTER = CategoryRouter.getInstance();
        this.FACADE = FacadeCategory.getInstance();
        this.addUpdate = addUpdate;
    }

    /**
     * TODO add
     * Triggered when the user pushes the add button
     * @param event
     */
    public void submitAddCategory(ActionEvent event){
        String nameCat = nameCatTF.getText();
        String descriptionCat = descriptionCatTF.getText();
        System.out.println("You try to add");
    }

    /**
     * TODO update
     * Triggered when the user pushes the update button
     * @param event
     */
    public void submitUpdateCategory(ActionEvent event){
        String newNameCat = nameCatTF.getText();
        String newDescriptionCat = descriptionCatTF.getText();
        System.out.println("You try to update");
    }

    /**
     * Function called to instantiate and display the label and buttons
     * when we want to add
     */
    private void caseAdd(){
        this.addUpdateL.setText("Add a New Category");
        addUpdateB.setText("Add");
        addUpdateB.setOnAction(this::submitAddCategory);
        nameCatTF.setOnAction(this::submitAddCategory);
    }

    /**
     * TODO cancel button
     */

    /**
     * Function called to instantiate and display the label and buttons when we want to update
     */
    private void caseUpdate(){
        this.addUpdateL.setText("Update a Category");
        addUpdateB.setText("Update");
        addUpdateB.setOnAction(this::submitUpdateCategory);
        nameCatTF.setOnAction(this::submitUpdateCategory);
    }

    /**
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(this.addUpdate == 0){
            this.caseAdd();
        }
        else{
            this.caseUpdate();
        }
    }
}
