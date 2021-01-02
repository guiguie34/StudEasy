package com.github.studeasy.gui.controller.commandofService;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CommandOfServiceRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeCommandOfService;
import com.github.studeasy.logic.facades.FacadeService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoricController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter COMMAND_ROUTER;

    /**
     * The facade CommandOfService used by the controller
     */
    private final FacadeCommandOfService FACADE_COMMANDOFSERVICE;


    /**
     * Table column of the status
     */
    @FXML
    private TableColumn<CommandOfService,String> historicColumn;

    /**
     * Create the controller with the router, the facade
     */
    public HistoricController(){
        this.COMMAND_ROUTER = CommandOfServiceRouter.getInstance();
        this.FACADE_COMMANDOFSERVICE = FacadeCommandOfService.getInstance();
    }

    /**
     * Triggered when the user pushed the cancel button
     * Cancel the action and redirect to the home of the user
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        COMMAND_ROUTER.changeView(UserRouter.HOME_STUDENT_FXML_PATH,event);
    }

    /**
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
