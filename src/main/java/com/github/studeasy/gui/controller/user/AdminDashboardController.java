package com.github.studeasy.gui.controller.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     /**
     * The facade used by the controller
     */
    private final FacadeUser FACADE;

    public AdminDashboardController(){
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /**
     * Load the view to manage Partner account
     * @param event the action trigerring the change of view
     * @throws IOException if an error occurs
     */
    public void managePartner(ActionEvent event) throws IOException {
        ((UserRouter)ROUTER).managePartner(event);
    }
}
