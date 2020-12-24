package com.github.studeasy.gui.controller.service;

import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.User;
import javafx.beans.property.SimpleObjectProperty;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller used to see all services of a user
 */
public class AllServicesController extends AbstractViewServicesController implements Initializable {

    /**
     * Table column of the status
     */
    @FXML
    private TableColumn<Service,String> ownerColumn;

    /**
     * Create the controller with the router, the facade
     * @param pendingAllServices indicates if we manage or if we see services
     */
    public AllServicesController(int pendingAllServices){
        super(pendingAllServices);
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
        // The owner of the service
        ownerColumn.setCellValueFactory(c -> {
            Service serv = c.getValue();
            User owner = serv.getOwner();
            return new SimpleObjectProperty<>(owner.getFirstname()+" "+owner.getLastname());
        });
        // We ask the parent to initialize some part of the table
        this.commonInitialize();
    }
}