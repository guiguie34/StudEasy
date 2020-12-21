package com.github.studeasy.gui.controller.service;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.facades.FacadeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
     /**
     * The facade used by the controller
     */
    private final FacadeService FACADE;

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
     * Create the controller with the router, the facade
     */
    public ProposeAskServiceController(int proposeRequest){
        this.ROUTER = ServiceRouter.getInstance();
        this.FACADE = FacadeService.getInstance();
        this.proposeRequest = proposeRequest;
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
    }
}
