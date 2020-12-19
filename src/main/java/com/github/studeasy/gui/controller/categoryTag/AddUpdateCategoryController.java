package com.github.studeasy.gui.controller.categoryTag;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CategoryRouter;
import com.github.studeasy.logic.facades.FacadeCategory;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.io.IOException;
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
     * The label displaying why the action failed
     */
    @FXML
    private Label addUpdateCatFailed;

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
     * Triggered when the user pushes the add button
     * Ask the facade to add a new category
     * @param event the event triggered
     */
    public void submitAddCategory(ActionEvent event) {
        // We retrieve the inputs of the user
        String nameCat = nameCatTF.getText();
        String descriptionCat = descriptionCatTF.getText();
        try{
            // Ask the facade to add the new category
            FACADE.submitAddCategory(nameCat,descriptionCat);
            // We redirect to the categories management page
            ROUTER.adminRestricted(CategoryRouter.MANAGE_CATEGORY_FXML_PATH,event);
        }
        // If there is an error, we display it to the user
        catch(BadInformationException err){
            addUpdateCatFailed.setTextFill(Paint.valueOf("red"));
            addUpdateCatFailed.setText(err.getMessage());
        }
        catch (IOException err){
            err.printStackTrace();
        }
    }

    /**
     * TODO update
     * Triggered when the user pushes the update button
     * Ask the facade to update a category
     * @param event
     */
    public void submitUpdateCategory(ActionEvent event){
        String newNameCat = nameCatTF.getText();
        String newDescriptionCat = descriptionCatTF.getText();
        System.out.println("You try to update");
    }

    /**
     * Triggered when the user wants to cancel
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        ROUTER.adminRestricted(CategoryRouter.MANAGE_CATEGORY_FXML_PATH,event);
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
