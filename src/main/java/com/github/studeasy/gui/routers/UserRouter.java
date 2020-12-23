package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.partner.AddUpdatePartnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javax.swing.*;
import java.io.IOException;

/**
 * Router for the user's routes
 */
public class UserRouter extends AbstractRouter {

    /**
     * Singleton of the user router
     */
    private static UserRouter userRouter = null;

    /**
     * Calls the parent constructor, getting the
     * instance of the session
     */
    private UserRouter(){
        super();
    }

    /**
     * Used to return the unique instance of the UserRouter
     * @return
     */
    public static UserRouter getInstance(){
        if(userRouter == null){
            userRouter = new UserRouter();
        }
        return userRouter;
    }

    /**
     * Path to the register view
     */
    public final static String REGISTER_FXML_PATH = "views/user/register.fxml";
    /**
     * Path to the home student view
     */
    private final static String HOME_STUDENT_FXML_PATH = "views/user/homeStudent.fxml";
    /**
     * Path to the home admin view
     */
    private final static String HOME_ADMIN_FXML_PATH = "views/user/homeAdmin.fxml";
    /**
     * Path to the home partner view
     */
    private final static String HOME_PARTNER_FXML_PATH = "views/user/homePartner.fxml";

    /**
     * Path to the home partner view
     */
    private final static String ADD_PARTNER_FXML_PATH = "views/partner/addUpdatePartner.fxml";

    /**
     * Path to the home partner view
     */
    private final static String MANAGE_PARTNER_FXML_PATH = "views/partner/partnerManagement.fxml";

    /**
     * Function loading the appropriate view for the connecting user
     * @param event the action trigerring this method
     * @throws IOException
     */
    public void login(ActionEvent event) throws  IOException {
        studentRestricted(HOME_STUDENT_FXML_PATH,event);
        adminRestricted(HOME_ADMIN_FXML_PATH,event);
        partnerRestricted(HOME_PARTNER_FXML_PATH,event);
    }

    /**
     * Function loading the appropriate view to add partner for an admin
     * @param event the action triggering this method
     * @throws IOException if an error occurs
     */
    public void addOrUpdatePartner(ActionEvent event,int addUpdate,Object partnerToUpdate) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(ADD_PARTNER_FXML_PATH));
        // We create the controller with addUpdate telling if we add or update
        AddUpdatePartnerController addUpdateCategoryController = new AddUpdatePartnerController(addUpdate,partnerToUpdate);
        // We link this controller with the FXML
        loader.setController(addUpdateCategoryController);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event,root);
        //adminRestricted(ADD_PARTNER_FXML_PATH,event);
    }

    /**
     * Function loading the appropriate view to manage the partner
     * @param event
     * @throws IOException
     */
    public void managePartner(ActionEvent event) throws IOException{
        adminRestricted(MANAGE_PARTNER_FXML_PATH,event);
    }
    /**
     * Function loading dashboard for each kind of user
     * @param event the action triggering this method
     * @throws IOException if an error occurs
     */
    public void backToDashboard(ActionEvent event) throws IOException {
        studentRestricted(HOME_STUDENT_FXML_PATH,event);
        adminRestricted(HOME_ADMIN_FXML_PATH,event);
        partnerRestricted(HOME_PARTNER_FXML_PATH,event);
    }
}