package com.github.studeasy.gui.controller.home;

import com.github.studeasy.gui.routers.*;

import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller of the student view
 */
public class HomeStudentController extends HomeAbstractController implements Initializable {

    /**
     * The service router used by the controller
     */
    private final AbstractRouter SERVICE_ROUTER;

    /**
     * The service router used by the controller
     */
    private final AbstractRouter JOB_ROUTER;

    public HomeStudentController(){
        super();
        this.SERVICE_ROUTER = ServiceRouter.getInstance();
        this.JOB_ROUTER = JobRouter.getInstance();
    }

    /**
     * Triggered when the user wants to go to his profile page
     * @param event the event triggered
     */
    public void loadMyProfile(ActionEvent event){
        try {
            ((UserRouter)ROUTER).profileUser(event);

        }catch (Exception e){
            e.printStackTrace();
            failLabel.setAlignment(Pos.CENTER);
            failLabel.setTextFill(Paint.valueOf("red"));
            failLabel.setText("Error, try again later");
        }
    }

    /**
     * Triggered when the user wants to propose a service
     * @param event the event triggered
     */
    public void proposeService(ActionEvent event){
        try {
            ((ServiceRouter) SERVICE_ROUTER).proposeOrRequestService(ServiceRouter.PROPOSE_ASK_SERVICE_FXML_PATH,event,0,2,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user wants to request a service
     * @param event the event triggered
     */
    public void requestService(ActionEvent event){
        try {
            ((ServiceRouter) SERVICE_ROUTER).proposeOrRequestService(ServiceRouter.PROPOSE_ASK_SERVICE_FXML_PATH,event,1,2,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user wants to see his services
     * @param event the event triggered
     */
    public void myServices(ActionEvent event){
        try {
            ((ServiceRouter) SERVICE_ROUTER).viewAllServices(ServiceRouter.MY_SERVICES_FXML_PATH,event,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user wants to see all the services
     * @param event the event triggered
     */
    public void seeAllServices(ActionEvent event){
        try {
            ((ServiceRouter) SERVICE_ROUTER).viewAllServices(ServiceRouter.ALL_SERVICES_FXML_PATH,event,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user wants to see all the services
     * @param event the event triggered
     */
    public void seePendingCommands(ActionEvent event){
        try {
            ROUTER.studentRestricted(CommandOfServiceRouter.VIEW_ALL_DEMANDE_SERVICE_FXML_PATH,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user wants to see all the historic of the command
     * @param event
     */
    public void seeHistoric(ActionEvent event){
        try{
            ROUTER.studentRestricted(CommandOfServiceRouter.SERVICES_BOUGHT_FXML_PATH,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Triggered when the user wants to see all the coupons
     * @param event the event triggered
     */
    public void buyCoupons(ActionEvent event){
        try {
            ROUTER.changeView(CouponRouter.COUPON_FXML_PATH,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the view to see all jobs
     * @param event the action trigerring the change of view
     * @throws IOException if an error occurs
     */
    public void seeJobs(ActionEvent event) throws IOException {
        ((JobRouter)JOB_ROUTER).viewJobs(event);
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
