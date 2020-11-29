package com.github.studeasy.gui.routers.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import javafx.event.ActionEvent;

import java.io.IOException;

public class RouterUser extends AbstractRouter {

    public void register(ActionEvent event) throws IOException {
        changeView("Views/register.fxml",event);
    }
    public void homeStudent(ActionEvent event) throws IOException{
        changeView("Views/homeStudent.fxml",event);
    }

    public void homePartner(ActionEvent event) throws IOException{
        changeView("Views/homePartner.fxml",event);
    }

    public void homeAdmin(ActionEvent event) throws IOException{
        changeView("Views/homeAdmin.fxml",event);
    }
}
