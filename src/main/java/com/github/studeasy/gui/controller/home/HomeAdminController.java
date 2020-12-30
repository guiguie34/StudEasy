package com.github.studeasy.gui.controller.home;

import com.github.studeasy.gui.controller.notifications.ButtonNotificationController;
import com.github.studeasy.gui.routers.*;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller of the admin view
 */
public class HomeAdminController extends HomeAbstractController implements Initializable {

    /**
     * The service router used by the controller
     */
    private final AbstractRouter SERVICE_ROUTER;

    /**
     * The category router used by the controller
     */
    private final AbstractRouter CATEGORY_ROUTER;

    /**
     * The job router used by the controller
     */
    private final AbstractRouter JOB_ROUTER;



    public HomeAdminController(){
        super();
        this.SERVICE_ROUTER = ServiceRouter.getInstance();
        this.CATEGORY_ROUTER = CategoryRouter.getInstance();
        this.JOB_ROUTER = JobRouter.getInstance();
    }

    /**
     * Go to the search users pages
     */
    public void searchUsers(ActionEvent event){
        try {
            if(Session.getInstance().isAdmin()){
                ((UserRouter)ROUTER).searchUsers(event);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
        ((JobRouter)JOB_ROUTER).viewJobs(event);
    }

    /**
     * Triggered when the admin wants to go to the categories management page
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void manageCategory(ActionEvent event) throws IOException {
        ROUTER.adminRestricted(CategoryRouter.MANAGE_CATEGORY_FXML_PATH,event);
    }

    /**
     * Triggered when the admin wants to manage the pending services
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void managePendingServices(ActionEvent event) throws IOException {
        ((ServiceRouter)SERVICE_ROUTER).viewAllServices(ServiceRouter.ALL_SERVICES_FXML_PATH,event,0);
    }

    /**
     * Triggered when the admin wants to see the services online
     * @param event the event triggered
     */
    public void seeAllServices(ActionEvent event){
        try {
            ((ServiceRouter)SERVICE_ROUTER).viewAllServices(ServiceRouter.ALL_SERVICES_FXML_PATH,event,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    }
}
