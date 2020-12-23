package com.github.studeasy.gui.controller.service;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * The controller used to see all services of a user
 */
public class AllServicesController implements Initializable {

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
    private TableColumn costColumn;

    /**
     * Table column of the date
     */
    @FXML
    private TableColumn<Service,String> dateColumn;

    /**
     * Table column of the status
     */
    @FXML
    private TableColumn<Service,String> ownerColumn;

    /**
     * Table column of the type of service
     */
    @FXML
    private TableColumn<Service,String> typeServiceColumn;

    /**
     * Table column for the category
     */
    @FXML
    private TableColumn<Service,String> categoryColumn;

    /**
     * The label showing what services are displayed
     */
    @FXML
    private Label pendingAllServicesL;

    /**
     * The list of the services displayed in the table
     */
    private ObservableList<Service> servicesList;

    /**
     * Indicates what services we should display
     * 0 the pending services
     * 1 all the services
     */
    private int pendingAllServices;

    /**
     * Create the controller with the router, the facade
     */
    public AllServicesController(int pendingAllServices){
        this.ROUTER = ServiceRouter.getInstance();
        this.FACADE_SERVICE = FacadeService.getInstance();
        this.pendingAllServices = pendingAllServices;
    }

    /**
     * Triggered when a user double clicks on a service, to see
     * the information of the service
     * @param event the event triggered
     * @param service the service the user wants to see
     * @throws IOException if an error occurs
     */
    public void viewService(MouseEvent event, Service service) {
        try {
            ((ServiceRouter) ROUTER).viewService(ServiceRouter.VIEW_SERVICE_FXML_PATH,event,service,pendingAllServices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user wants to go back
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        Session session = Session.getInstance();
        if(session.isAdmin()){
            ROUTER.adminRestricted(UserRouter.HOME_ADMIN_FXML_PATH,event);
        }
        if(session.isStudent()){
            ROUTER.studentRestricted(UserRouter.HOME_STUDENT_FXML_PATH,event);
        }
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
        // In case we don't have any services
        servicesTV.setPlaceholder(new Label("There is no service to display"));
        // According to the role of the user, we won't display the same things
        // We retrieve all the services and put them in an observable list
        Session session = Session.getInstance();
        // Admin managing the pending services
        if (session.isAdmin() && pendingAllServices == 0) {
            this.pendingAllServicesL.setText("Pending Services");
            // We only retrieve the pending services
            servicesList = FXCollections.observableArrayList(FACADE_SERVICE.getPendingServices());
        }
        // We display all the services
        else {
            this.pendingAllServicesL.setText("All Services Online");
            // We retrieve all the services
            servicesList = FXCollections.observableArrayList(FACADE_SERVICE.getOnlineServices());
        }
        // We put the titles of the services on the right column
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<Service,String>("title")
        );
        // Same for the cost
        costColumn.setCellValueFactory(
                new PropertyValueFactory<Service,Integer>("cost")
        );
        // We give a better format to the date
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);
        // The date of creation
        dateColumn.setCellValueFactory(c -> {
            Service serv = c.getValue();
            Date dateCreation = serv.getDateCreation();
            return new SimpleObjectProperty<String>(shortDateFormat.format(dateCreation));
        });
        // The type of service
        typeServiceColumn.setCellValueFactory(c -> {
            // We retrieve the service
            Service serv = c.getValue();
            switch(serv.getTypeService()){
                case 0:
                    // Service proposed
                    return new SimpleObjectProperty<>("Proposed");
                case 1:
                    // Service requested
                    return new SimpleObjectProperty<>("Requested");
                default:
                    return new SimpleObjectProperty<>("Unknown");
            }
        });
        // The category of the service
        categoryColumn.setCellValueFactory(c -> {
            Service serv = c.getValue();
            CategoryTag cat = serv.getCategory();
            return new SimpleObjectProperty<>(cat.getName());
        });
        // The owner of the service
        ownerColumn.setCellValueFactory(c -> {
            Service serv = c.getValue();
            User owner = serv.getOwner();
            return new SimpleObjectProperty<>(owner.getFirstname()+" "+owner.getLastname());
        });

        // We add the data in the table
        servicesTV.setItems(servicesList);

        // Put a listener (double click) on each row to go to the information of a service
        servicesTV.setRowFactory( tv -> {
            TableRow<Service> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Service rowData = row.getItem();
                    this.viewService(event,rowData);
                }
            });
            return row ;
        });
    }
}