package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.user.InfoUserController;
import com.github.studeasy.gui.controller.user.RegisterUpdateController;
import com.github.studeasy.logic.common.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
     * Load the view and display the good label and buttons for register page
     * or update his profile page
     * @param pathFXML the path to the fxml file
     * @param event the action triggering the change of view
     * @param registerUpdate 0 if we add, 1 if we register
     * @throws IOException
     */
    public void registerOrUpdateUser(String pathFXML, ActionEvent event, int registerUpdate) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with
        RegisterUpdateController registerUpdateController = new RegisterUpdateController(registerUpdate);
        loader.setController(registerUpdateController);
        Parent root = loader.load();
        this.changeView(event,root);
    }

    /**
     *  Load the page of a specific user
     * @param pathFXML the path to the fxml file
     * @param event the action triggering the change of view
     * @param user the user to display
     * @throws IOException
     */
    public void viewUser(String pathFXML, Event event, User user) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with
        InfoUserController infoUserController = new InfoUserController(user);
        loader.setController(infoUserController);
        Parent root = loader.load();
        this.changeView(event,root);
    }

    /**
     * Load the view and display all the information related to the profile
     * of a student
     * @param event the action triggering the change of view
     * @throws IOException
     */
    public void profileUser(ActionEvent event) throws IOException {
        changeView(PROFILE_USER_FXML_PATH, event);
    }

    /**
     * Path to the profile view
     */
    public final static String PROFILE_USER_FXML_PATH = "views/user/myProfile.fxml";
    /**
     * Path to the register view
     */
    public final static String REGISTER_FXML_PATH = "views/user/register.fxml";
    /**
     * Path to the home student view
     */
    public final static String HOME_STUDENT_FXML_PATH = "views/user/homeStudent.fxml";
    /**
     * Path to the home admin view
     */
    public final static String HOME_ADMIN_FXML_PATH = "views/user/homeAdmin.fxml";
    /**
     * Path to the home partner view
     */
    private final static String HOME_PARTNER_FXML_PATH = "views/user/homePartner.fxml";

    /**
     * Path to searchUser view
     */
    public final static String SEARCH_USER_FXML_PATH = "views/user/searchUsers.fxml";

    /**
     * Path to viewUser view
     */
    public final static String VIEW_USER_FXML_PATH = "views/user/viewUser.fxml";


    /**
     * Function loading the appropriate view for the connecting user
     * @param event the action triggering this method
     * @throws IOException
     */
    public void login(ActionEvent event) throws  IOException {
        studentRestricted(HOME_STUDENT_FXML_PATH,event);
        adminRestricted(HOME_ADMIN_FXML_PATH,event);
        partnerRestricted(HOME_PARTNER_FXML_PATH,event);
    }

    /**
     * Load the view and display all students
     * of a student
     * @param event the action triggering the change of view
     * @throws IOException
     */
    public void searchUsers(ActionEvent event) throws IOException {
        changeView(SEARCH_USER_FXML_PATH, event);
    }
}