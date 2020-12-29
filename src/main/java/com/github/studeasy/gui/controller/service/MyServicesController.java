package com.github.studeasy.gui.controller.service;

import com.github.studeasy.logic.common.Service;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller used to see the services of the user
 */
public class MyServicesController extends AbstractViewServicesController implements Initializable {

    /**
     * Table column of the status
     */
    @FXML
    private TableColumn<Service,Image> statusColumn;

    /**
     * Create the controller with the router, the facade
     * @param pendingAllServices indicates if we see our services, or if we see all services
     */
    public MyServicesController(int pendingAllServices){
        super(pendingAllServices);
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
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        // We ask the parent to initialize some part of the table
        this.commonInitialize();
    }
}
