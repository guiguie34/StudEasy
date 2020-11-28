package com.github.studeasy.gui.routers.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import javafx.event.ActionEvent;

import java.io.IOException;

public class RouterUser extends AbstractRouter {

    public void register(ActionEvent event) throws IOException {
        changeView("Views/register.fxml",event);
    }
}
