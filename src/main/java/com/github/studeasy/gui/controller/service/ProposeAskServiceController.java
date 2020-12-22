package com.github.studeasy.gui.controller.service;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.facades.FacadeCategory;
import com.github.studeasy.logic.facades.FacadeService;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller used to create a service
 */
public class ProposeAskServiceController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     * The facade service used by the controller
     */
    private final FacadeService FACADE_SERVICE;

    /**
     * The facade service used by the controller
     */
    private final FacadeCategory FACADE_CATEGORY;

    /**
     * Tells if the user wants to propose or request a service
     * 0 if he tries to propose, 1 if he tries to request
     */
    private int proposeRequest;

    /**
     * The label displaying propose or request a service
     */
    @FXML
    private Label proposeRequestL;

    /**
     * The label displaying the error that occured
     */
    @FXML
    private Label addServiceFailedL;

    /**
     * The textfield for the title of the service
     */
    @FXML
    private TextField titleServiceTF;

    /**
     * The textarea for the description of the service
     */
    @FXML
    private TextArea descriptionServiceTA;

    /**
     * The choice box for the category
     */
    @FXML
    private ChoiceBox<CategoryTag> categoriesCB;

    /**
     * The spinner to enter the price of the service
     */
    @FXML
    private Spinner<Integer> costServiceS;

    /**
     * Create the controller with the router, the facades
     */
    public ProposeAskServiceController(int proposeRequest){
        this.ROUTER = ServiceRouter.getInstance();
        this.FACADE_SERVICE = FacadeService.getInstance();
        this.FACADE_CATEGORY = FacadeCategory.getInstance();
        this.proposeRequest = proposeRequest;
    }

    /**
     * Triggered when the student tries to post his service
     * @param event the event triggered
     */
    public void submitService(ActionEvent event){
        // We retrieve what the user entered
        String titleS = this.titleServiceTF.getText();
        String descriptionS = this.descriptionServiceTA.getText();
        CategoryTag categoryS = this.categoriesCB.getValue();
        int costS = this.costServiceS.getValue();
        // The message displayed in the confirmation box
        String infoMessage = "Are you sure you want to add this service ?\n" +
                "You won't be able to modify it later";
        // We first check if the title and the description are not empty
        if(!titleS.isEmpty() && !descriptionS.isEmpty()){
            // We ask the student if he is sure to add the service
            if(AbstractRouter.confirmationBox(infoMessage,
                    "Confirmation: "+ proposeRequestL.getText(),
                    "Stud'Easy - Confirmation")){
                // We add the service
                try {
                    FACADE_SERVICE.submitService(titleS,descriptionS,categoryS,costS,proposeRequest);
                    ROUTER.studentRestricted(UserRouter.HOME_STUDENT_FXML_PATH,event);
                } catch (BadInformationException err) {
                    addServiceFailedL.setTextFill(Paint.valueOf("red"));
                    addServiceFailedL.setText(err.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            addServiceFailedL.setTextFill(Paint.valueOf("red"));
            addServiceFailedL.setText("All the fields must be completed");
        }
    }

    /**
     * Triggered when the user wants to cancel
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        ROUTER.studentRestricted(UserRouter.HOME_STUDENT_FXML_PATH,event);
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
        // We check if the user tries to propose or request a service
        if(proposeRequest == 1){
            // The student wants to propose a service
            proposeRequestL.setText("Request a service");
        }
        else{
            // The student wants to request a service
            proposeRequestL.setText("Propose a service");
        }
        // We fill the choice box with the categories
        this.categoriesCB.setItems(FXCollections.observableArrayList(FACADE_CATEGORY.seeAllCategories()));
        // The first item is selected by default
        this.categoriesCB.getSelectionModel().selectFirst();
    }
}
