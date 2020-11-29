package com.github.studeasy.gui.routers.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import javafx.event.ActionEvent;

import java.io.IOException;

public class RouterUser extends AbstractRouter {

    public void register(ActionEvent event) throws IOException {
        changeView("Views/register.fxml",event);
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void login(ActionEvent event) throws  IOException {
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

    private void homeStudent(ActionEvent event) throws IOException{
        changeView("Views/homeStudent.fxml",event);
    }

    private void homePartner(ActionEvent event) throws IOException{
        changeView("Views/homePartner.fxml",event);
    }

    private void homeAdmin(ActionEvent event) throws IOException{
        changeView("Views/homeAdmin.fxml",event);
    }
}
