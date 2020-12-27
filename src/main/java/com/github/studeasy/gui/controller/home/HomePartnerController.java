package com.github.studeasy.gui.controller.home;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.JobRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller of the partner view
 */
public class HomePartnerController extends HomeAbstractController implements Initializable {


    public HomePartnerController(){
        super();
    }

    /**
     * Load the view to handle job
     * @param event the action triggering the change of view
     * @throws IOException if an error occurs
     */
    public void handleJob(ActionEvent event) throws IOException {
        ((JobRouter)ROUTER).viewJobs(event);
    }

    public void addJob(ActionEvent event) throws IOException {
        ((JobRouter)ROUTER).addOrUpdateJob(event,0,null);
    }

    /**
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
