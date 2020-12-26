package com.github.studeasy.gui.controller.partner;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.common.role.Role;
import com.github.studeasy.logic.common.role.RolePartner;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handle the partner management
 */
public class PartnerManagementController implements Initializable {



    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     /**
     * The facade used by the controller
     */
    private final FacadeUser FACADE;

    /**
     * The table containing the partner
     */
    @FXML
    private TableView<User> tablePartner;

    /**
     * The field to research the partner
     */
    @FXML
    private TextField searchPartnerTF;

    /**
     * Column containing the firstname of partner
     */
    @FXML
    private TableColumn<User,String> firstnameColumn;

    /**
     * Column containing the firstname of partner
     */
    @FXML
    private TableColumn<User,String> lastnameColumn;

    /**
     * Column containing the firstname of partner
     */
    @FXML
    private TableColumn<User,String> companyColumn;

    /**
     * Column containing buttons to update
     */
    @FXML
    private TableColumn<User, Button> updateColumn;

    /**
     * Column containing buttons to delete
     */
    @FXML
    private TableColumn<User, Button> deleteColumn;

    /**
     * All the partner displayed in the table
     */
    private ObservableList<User> partnerList= FXCollections.observableArrayList();


    public PartnerManagementController(){
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
    }


    /**
     * Load the view to return to the admin dashboard
     * @param event The event triggering the function
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        ((UserRouter)ROUTER).backToDashboard(event);
    }

    /**
     * Load the view to manage Partner account
     * @param event the action trigerring the change of view
     * @throws IOException if an error occurs
     */
    public void addPartner(ActionEvent event) throws IOException {
        ((UserRouter)ROUTER).addOrUpdatePartner(event,0,null);
    }


    /**
     * Delete the partner from the system
     * @param event The event that triggering the action
     * @param user The user to be deleted
     */
    public void deletePartner(ActionEvent event, Object user) {
        if (AbstractRouter.confirmationBox("Are you sure you want to delete this partner ?",
                "Confirmation of the deletion: " + ((User) user).getFirstname() + " " + ((User) user).getLastname(),
                "Stud'Easy - Confirmation")) {
            try {
                FACADE.deletePartner((User)user);
                partnerList.removeAll((User)user);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Load the view to update a partner
     * @param event The event triggering the method
     * @param user The user to be updated
     */
    public void updatePartner(ActionEvent event, Object user) throws IOException {
        ((UserRouter)ROUTER).addOrUpdatePartner(event,1,(User) user);

    }
    /**
     * Add buttons for each row of a column
     * @param currentController
     * @param tabC column
     */
    private void addButtonsToTable(PartnerManagementController currentController, TableColumn tabC) {
        // cellFactory will contain our buttons
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<>() {
            // For each row of the table view, we want to create the buttons
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<>() {
                    // The image replacing the button
                    Image img;
                    // And the displayer of the image
                    final ImageView iv;
                    // We create the button
                    private final Button btn = new Button();

                    {
                        // We want it transparent, so we only see the image
                        btn.setStyle("-fx-background-color: transparent;");
                        // We assign the right button to the right column
                        switch (tabC.getId()) {
                            // Update column
                            case ("updateColumn"):
                                img = new Image("images/common/update.png");
                                btn.setOnAction(event -> {
                                    try {
                                        currentController.updatePartner(event, getTableView().getItems().get(getIndex()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                                break;
                            // Delete column
                            case ("deleteColumn"):
                                img = new Image("images/common/trash.png");
                                btn.setOnAction(event -> currentController.deletePartner(event, getTableView().getItems().get(getIndex())));
                                break;
                        }
                        iv = new ImageView(img);
                        btn.setGraphic(iv);
                    }

                    // We only want to print the buttons on the rows containing data
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        // We add the buttons to the column
        tabC.setCellFactory(cellFactory);
    }

    /**
     * Method to search a partner by his firstname
     * @param observable
     * @param oldValue
     * @param newValue
     * @param filteredData
     */
    public void searchPartner(ObservableValue<? extends String> observable, String oldValue, String newValue, FilteredList<User> filteredData) {
        filteredData.setPredicate(partner -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (partner.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                // The category is displayed
                return true;
            } else {
                // The name doesn't match, not displayed
                return false;
            }
        });
    }

    /**
     * Allows to display the partner / buttons and to load search method
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            partnerList.addAll(FACADE.getAllPartner());
            tablePartner.setItems(partnerList);

            firstnameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("firstname"));
            lastnameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("lastname"));
            companyColumn.setCellValueFactory(new PropertyValueFactory<User,String>("role"));

            addButtonsToTable(this, updateColumn);
            addButtonsToTable(this, deleteColumn);

            FilteredList<User> filteredCategories = new FilteredList<>(partnerList, p -> true);
            searchPartnerTF.textProperty().addListener((observable,oldValue,newValue) -> this.searchPartner(observable,oldValue,newValue,filteredCategories));
            SortedList<User> sortedData = new SortedList<>(filteredCategories);
            sortedData.comparatorProperty().bind(tablePartner.comparatorProperty());
            tablePartner.setItems(sortedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
