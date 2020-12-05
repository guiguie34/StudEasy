package com.github.studeasy.gui.routers;

import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Router for the user's routes
 */
public class UserRouter extends AbstractRouter {

    /**
     * Calls the parent constructor, getting the
     * instance of the session
     */
    private UserRouter(){
        super();
    }

    /**
     * Lazy holder of the user router
     */
    private static class loadRouter{
        public static final UserRouter instance= new UserRouter();
    }

    /**
     * Used to return the unique instance of the UserRouter
     * @return
     */
    public static UserRouter getInstance(){
        return UserRouter.loadRouter.instance;
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