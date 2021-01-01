package com.github.studeasy.gui.controller.commandofService;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CommandOfServiceRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.facades.FacadeCategory;
import com.github.studeasy.logic.facades.FacadeCommandOfService;
import com.github.studeasy.logic.facades.FacadeService;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BuyOrApplyServiceController implements Initializable  {
    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     * The facade CommandOfService used by the controller
     */
    private final FacadeCommandOfService FACADE_COMMANDOFSERVICE;

    /**
     * The facade service used by the controller
     */
    private final FacadeService FACADE_SERVICE;

    /**
     * Tells if the user wants to buy or apply a service
     * 0 if he tries to buy, 1 if he tries to apply
     */
    private int commandRequest;

    /**
     * The label displaying apply or buy a service
     */
    @FXML
    private Label commandRequestL;

    /**
     * The label displaying the error that occured
     */
    @FXML
    private Label commandServiceFailedL;

    /**
     * The text for the title of the service
     */
    @FXML
    private Text titleServiceTF;

    /**
     * The text for the description of the service
     */
    @FXML
    private Text descriptionServiceTA;

    /**
     * The cost of service
     */
    @FXML
    private Text costServiceS;

    /**
     * The command to add
     */
    @FXML
    private CommandOfService command;

    /**
     * Create the controller with the router, the facades
     */
    public BuyOrApplyServiceController(int commandRequest, CommandOfService command){
        this.ROUTER = CommandOfServiceRouter.getInstance();
        this.FACADE_COMMANDOFSERVICE = FacadeCommandOfService.getInstance();
        this.FACADE_SERVICE = FacadeService.getInstance();
        this.commandRequest = commandRequest;
        this.command = command;
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
        // We check if the user tries to buy or apply a service
        if(commandRequest == 1){
            // The student wants to by a service
            commandRequestL.setText("Buy a service");
            //addUpdateB.setText("Post");
            //addUpdateB.setOnAction(this::submitService);
        }
        else{
            // The student wants to apply
            commandRequestL.setText("Update a service");

        }

    }
}
