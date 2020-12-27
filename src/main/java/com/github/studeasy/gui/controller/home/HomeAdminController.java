package com.github.studeasy.gui.controller.home;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CategoryRouter;
import com.github.studeasy.gui.routers.JobRouter;
import com.github.studeasy.gui.routers.FeedbackRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller of the admin view
 */
public class HomeAdminController extends HomeAbstractController implements Initializable {


    public HomeAdminController(){
        super();
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
        (JobRouter.getInstance()).viewJobs(event);
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
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
