package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.user.RegisterUpdateController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
     * Load the view and display the good label and buttons for adding or
     * updating a category
     * @param pathFXML the path to the fxml file
     * @param event the action trigerring the change of view
     * @param addUpdate 0 if we add, 1 if we update
     * @throws IOException
     */
    public void registerOrUpdateUser(String pathFXML, ActionEvent event, int addUpdate) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with
        RegisterUpdateController registerUpdateController = new RegisterUpdateController(addUpdate);
        loader.setController(registerUpdateController);
        Parent root = loader.load();
        this.changeView(event,root);
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
     * Function loading the appropriate view for the connecting user
     * @param event the action trigerring this method
     * @throws IOException
     */
    public void login(ActionEvent event) throws  IOException {
        studentRestricted(HOME_STUDENT_FXML_PATH,event);
        adminRestricted(HOME_ADMIN_FXML_PATH,event);
        partnerRestricted(HOME_PARTNER_FXML_PATH,event);
    }
}