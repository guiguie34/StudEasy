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
 * The controller of the admin view
 */
public class HomeAdminController implements Initializable {
    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     /**
     * The facade used by the controller
     */
    private final FacadeUser FACADE;

    public HomeAdminController(){
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
    }

    /**
     * Load the view to manage Partner account
     * @param event the action trigerring the change of view
     * @throws IOException if an error occurs
     */
    public void managePartner(ActionEvent event) throws IOException {
        ((UserRouter)ROUTER).managePartner(event);
    }

    /**
     * Load the view to manage validate pending job
     * @param event the action trigerring the change of view
     * @throws IOException if an error occurs
     */
    public void manageJob(ActionEvent event) throws IOException {
        (JobRouter.getInstance()).handleJob(event);
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
