package com.github.studeasy.gui.controller.commandofService;

import com.github.studeasy.gui.routers.*;
import com.github.studeasy.logic.common.*;
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
    private final AbstractRouter COMMAND_ROUTER;

    /**
     * The facade CommandOfService used by the controller
     */
    private final FacadeCommandOfService FACADE_COMMANDOFSERVICE;

    /**
     * The facade service used by the controller
     */
    private final FacadeService FACADE_SERVICE;

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
     * Service concerned
     */
    private final Service service;

    /**
     * The command to add
     */
    @FXML
    private CommandOfService command;

    /**
     * Create the controller with the router, the facades
     */
    public BuyOrApplyServiceController(CommandOfService command,Service service){
        this.COMMAND_ROUTER = CommandOfServiceRouter.getInstance();
        this.FACADE_COMMANDOFSERVICE = FacadeCommandOfService.getInstance();
        this.FACADE_SERVICE = FacadeService.getInstance();
        this.command = command;
        this.service=service;
    }

    /***
     *
     * @param event
     */
    public void buyOrApplyController(ActionEvent event) throws Exception {
        // We get the current user
        Session session = Session.getInstance();
        User user = session.getCurrentUser();
        if(service.getTypeService()==1){
            FACADE_COMMANDOFSERVICE.buyService(service,user);
        }
        else{
            FACADE_COMMANDOFSERVICE.applyForService(service,user);
        }
        ((CommandOfServiceRouter)COMMAND_ROUTER).buyOrApplyService(CommandOfServiceRouter.BUY_SERVICE_FXML_PATH,event,command,service);


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
        // We check if the user buy or apply service
        if(command.getService().getTypeService() == 0){
            // The student wants to buy a service
            commandRequestL.setText("Buy a service");
        }
        else{
            // The student wants to apply
            commandRequestL.setText("Update a service");
        }

    }
}
