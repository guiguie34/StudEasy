package com.github.studeasy.gui.controller.service;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.facades.FacadeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller used to see the services of the user
 */
public class MyServicesController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     * The facade service used by the controller
     */
    private final FacadeService FACADE_SERVICE;

    /**
     * Table view of the services
     */
    @FXML
    private TableView servicesTV;

    /**
     * Table column of the titles
     */
    @FXML
    private TableColumn titleColumn;

    /**
     * Table column of the descriptions
     */
    @FXML
    private TableColumn descColumn;

    /**
     * Table column of the state
     */
    @FXML
    private TableColumn stateColumn;

    /**
     * The list of the services displayed in the table
     */
    private ObservableList<Service> servicesList;

    /**
     * Create the controller with the router, the facade
     */
    public MyServicesController(){
        this.ROUTER = ServiceRouter.getInstance();
        this.FACADE_SERVICE = FacadeService.getInstance();
    }

    /**
     * Triggered when the user wants to go back
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        ROUTER.studentRestricted(UserRouter.HOME_STUDENT_FXML_PATH,event);
    }

    /**
     * TODO display all the main information of the services (image for the state !)
     * TODO double click --> see all the info of the service
     * TODO filter the proposed / requested ?
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // In case we don't have any services
        servicesTV.setPlaceholder(new Label("There is no service to display"));
        // We retrieve all the services and put them in an observable list
        servicesList = FXCollections.observableArrayList(FACADE_SERVICE.getMyServices());
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<Service,String>("title")
        );
        // Same for the descriptions
        descColumn.setCellValueFactory(
                new PropertyValueFactory<Service,String>("description")
        );
        servicesTV.setItems(servicesList);
    }
}
