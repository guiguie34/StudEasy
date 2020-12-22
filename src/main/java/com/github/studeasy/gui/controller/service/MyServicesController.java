package com.github.studeasy.gui.controller.service;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.facades.FacadeService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
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
    private TableColumn<Service,Image> statusColumn;

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
     * Used to add each image on each row
     */
    public void addImageColumn(){
        Callback<TableColumn<Service, Image>, TableCell<Service, Image>> cellFactory = new Callback<>() {
            // For each row of the table view, we want to have the image of the status
            @Override
            public TableCell<Service, Image> call(final TableColumn<Service, Image> param) {
                final TableCell<Service, Image> cell = new TableCell<Service, Image>() {
                    @Override
                    public void updateItem(Image item, boolean empty) {
                        super.updateItem(item, empty);
                        ImageView imageDisplayed = new ImageView(item);
                        setGraphic(imageDisplayed);
                    }
                };
                return cell;
            }
        };
        // We associate the cells to the table column
        statusColumn.setCellFactory(cellFactory);
    }

    /**
     * TODO double click --> see all the info of the service
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

        // Validated image
        final Image validatedPNG = new Image("images/service/validated.png");
        // Pending image
        final Image pendingPNG = new Image("images/service/pending.png");

        // We associate an image to the status of the service
        statusColumn.setCellValueFactory(c -> {
            Service service = c.getValue();
            switch(service.getStatus()) {
                case 0:
                    // Pending image
                    return new SimpleObjectProperty<>(pendingPNG);
                case 1:
                    // Validated image
                    return new SimpleObjectProperty<>(validatedPNG);
                default:
                    return null;
            }
        });

        // We apply it for each row
        this.addImageColumn();

        // We add the data in the table
        servicesTV.setItems(servicesList);
    }
}
