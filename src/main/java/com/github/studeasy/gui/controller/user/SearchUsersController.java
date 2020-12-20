package com.github.studeasy.gui.controller.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the search a user view
 */
public class SearchUsersController implements Initializable {

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
     * The field to search a user
     */
    @FXML
    private TextField searchCategoryTF;

    /**
     * The table containing the categories information
     */
    @FXML
    private TableView<User> studentManagement;

    /**
     * The column containing the first names of the users
     */
    @FXML
    private TableColumn<User, String> firstNameColumn;

    /**
     * The column containing the last name of the users
     */
    @FXML
    private TableColumn<User, String> lastNameColumn;

    /**
     * The column containing the emails of the users
     */
    @FXML
    private TableColumn<User, String> emailColumn;

    /**
     * The column containing the buttons to see an user
     */
    @FXML
    private TableColumn actionColumn;

    /**
     * All the users displayed in the table
     */
    private ObservableList<User> studentList;

    public SearchUsersController() {
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
    }

    /**
     * Triggered when the user pushed the cancel button
     * Cancel the action and redirect to the login page
     * @param event
     */
    public void cancel(ActionEvent event) {
        try {
            ROUTER.changeView(UserRouter.HOME_ADMIN_FXML_PATH,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the admin types text in the search text field
     * It filters the data to display only the users matching what is entered
     * @param observable
     * @param oldValue
     * @param newValue the new value entered by the administrator
     * @param filteredData the data filtered
     */
    public void searchUser(ObservableValue<? extends String> observable, String oldValue, String newValue, FilteredList<User> filteredData) {
        // We check for each category
        filteredData.setPredicate(user -> {
            // If the text field is empty, we display all the users
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // We compare what he entered (in lower case)
            String lowerCaseFilter = newValue.toLowerCase();
            // If the filter matches the email of an user
            // The user is displayed
            // The name doesn't match, not displayed
            return user.getEmailAddress().toLowerCase().contains(lowerCaseFilter);
        });
    }

    public void viewUser(ActionEvent event){
        try {
            ROUTER.changeView(UserRouter.HOME_ADMIN_FXML_PATH,event);
        } catch (IOException e) {
            e.printStackTrace();
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
        // In case we don't have any user
        studentManagement.setPlaceholder(new Label("There is currently no category to display"));
        // We retrieve all the categories and put them in an observable list
        studentList = FXCollections.observableArrayList(FACADE.seeAllUsers());
        // We put the names of the users on the right column
        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<User,String>("firstname")
        );
        // Same for the last name
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<User,String>("lastname")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<User,String>("emailAddress")
        );

        // We create a filtered list containing the data
        FilteredList<User> filteredUsers = new FilteredList<>(studentList, p -> true);
        // We associate the text field with a function listening to what is entered
        searchCategoryTF.textProperty().addListener((observable,oldValue,newValue) -> this.searchUser(observable,oldValue,newValue,filteredUsers));
        // We create a sorted list based on our filtered data
        SortedList<User> sortedData = new SortedList<>(filteredUsers);
        sortedData.comparatorProperty().bind(studentManagement.comparatorProperty());
        // We add the sorted data in the table
        studentManagement.setItems(sortedData);

        studentManagement.setRowFactory( tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User rowData = row.getItem();
                    System.out.println(rowData);
                }
            });
            return row ;
        });

    }
}
