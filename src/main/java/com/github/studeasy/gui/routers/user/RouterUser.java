package com.github.studeasy.gui.routers.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * The router for the user
 */
public class RouterUser extends AbstractRouter {

    /**
     * Function loading the register view
     * @param event, the action trigerring this method
     * @throws IOException
     */
    public void register(ActionEvent event) throws IOException {
        changeView("Views/register.fxml",event);
    }

    /**
     * Function loading the appropriate view for the connecting user
     * @param event, the action trigerring this method
     * @throws IOException
     */
    public void login(ActionEvent event) throws  IOException {
        System.out.println("COUCOU");
        if(session.isStudent()){
            homeStudent(event);
        }
        else if(session.isAdmin()){
            homeAdmin(event);
        }
        else if(session.isPartner()){
            homePartner(event);
        }
    }

    /**
     * Function loading the home student view
     * @param event, the action trigerring this method
     * @throws IOException
     */
    private void homeStudent(ActionEvent event) throws IOException{
        changeView("Views/homeStudent.fxml",event);
    }

    /**
     * Function loading the home partner view
     * @param event, the action trigerring this method
     * @throws IOException
     */
    private void homePartner(ActionEvent event) throws IOException{
        changeView("Views/homePartner.fxml",event);
    }

    /**
     * Function loading the home admin view
     * @param event, the action trigerring this method
     * @throws IOException
     */
    private void homeAdmin(ActionEvent event) throws IOException{
        changeView("Views/homeAdmin.fxml",event);
    }
}
