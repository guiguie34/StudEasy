package com.github.studeasy.gui.controller.categoryTag;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CategoryRouter;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.facades.FacadeCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The controller for the management of the categories
 */
public class CategoryManagementController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     /**
     * The facade used by the controller
     */
    private final FacadeCategory FACADE;

    /**
     * The field to search a category
     */
    @FXML
    private TextField searchCategoryTF;

    /**
     * The table containing the categories information
     */
    @FXML
    private TableView categoryTV;

    /**
     * The column containing the names of the categories
     */
    @FXML
    private TableColumn nameColumn;

    /**
     * The column containing the names of the categories
     */
    @FXML
    private TableColumn descColumn;

    /**
     * The column containing the buttons to update the categories
     */
    @FXML
    private TableColumn updateColumn;

    /**
     * The column containing the buttons to delete the categories
     */
    @FXML
    private TableColumn deleteColumn;

    /**
     * Instantiate the attributes with
     * a router and a facade used for categories
     */
    public CategoryManagementController(){
        this.ROUTER = CategoryRouter.getInstance();
        this.FACADE = FacadeCategory.getInstance();
    }

    /**
     * Triggered when the admin wants to go to the add/update a category page
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void addNewCategory(ActionEvent event) throws IOException {
        ((CategoryRouter)ROUTER).addOrUpdateCategory(CategoryRouter.ADD_UPDATE_CATEGORY_FXML_PATH,event,0);
    }

    /**
     * TODO do a real search
     * Triggered when the admin wants to search a particular category
     * @param event the event triggered
     */
    public void searchCategory(ActionEvent event) {
        String categorySearched = searchCategoryTF.getText();
        System.out.println("SEARCHING : "+categorySearched);
    }

    /**
     * TODO delete / update to implement
      */

    /**
     * Triggered when the user pushes one of the update button
     * @param event the event triggered
     * @param categoryToUpdate the category the user wants to update
     */
    public void updateCategory(ActionEvent event, CategoryTag categoryToUpdate){
        System.out.println("Data to update: " + categoryToUpdate.getName() + " " + categoryToUpdate.getDescription());
    }

    /**
     * Triggered when the user pushes one of the update button
     * @param event the event triggered
     * @param categoryToDelete the category the user wants to update
     */
    public void deleteCategory(ActionEvent event, CategoryTag categoryToDelete){
        System.out.println("Data to delete: " + categoryToDelete.getName() + " " + categoryToDelete.getDescription());
    }

    /**
     * TODO cancel button
     * Triggered when the user wants to go back
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        System.out.println("CANCEL");
    }

    /**
     * Function used at the initialization, to create and associate the update/delete buttons to the data
     * @param currentController the controller associated, to trigger the events with the buttons
     * @param tabC the column where we add the buttons (update or delete column)
     */
    private void addButtonsToTable(CategoryManagementController currentController, TableColumn tabC) {
        // cellFactory will contain our buttons
        Callback<TableColumn<CategoryTag, Void>, TableCell<CategoryTag, Void>> cellFactory = new Callback<TableColumn<CategoryTag, Void>, TableCell<CategoryTag, Void>>() {
            // For each row of the table view, we want to create the buttons
            @Override
            public TableCell<CategoryTag, Void> call(final TableColumn<CategoryTag, Void> param) {
                final TableCell<CategoryTag, Void> cell = new TableCell<CategoryTag, Void>() {

                    // The image replacing the button
                    Image img;
                    // And the displayer of the image
                    ImageView iv;

                    // We create the button
                    private final Button btn = new Button();
                    {
                        // We want it transparent, so we only see the image
                        btn.setStyle("-fx-background-color: transparent;");
                        // We assign the right button to the right column
                        switch(tabC.getId()){
                            // Update column
                            case("updateColumn"):
                                img = new Image("images/common/update.png");
                                btn.setOnAction(event -> currentController.updateCategory(event,getTableView().getItems().get(getIndex())));
                                break;
                            // Delete column
                            case("deleteColumn"):
                                img = new Image("images/common/trash.png");
                                btn.setOnAction(event -> currentController.deleteCategory(event,getTableView().getItems().get(getIndex())));
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
     * Function used to retrieve the categories, and to display them
     * in a table with buttons allowing to update or delete them
     */
    private void fillTable(){
        // We retrieve all the categories and put them in an observable list;
        final ObservableList<CategoryTag> categoriesList = FXCollections.observableArrayList(FACADE.seeAllCategories());
        // We put the names of the categories on the right column
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<CategoryTag,String>("name")
        );
        // Same for the descriptions
        descColumn.setCellValueFactory(
                new PropertyValueFactory<CategoryTag,String>("description")
        );
        // We add the update buttons
        addButtonsToTable(this, updateColumn);
        // We add the delete buttons
        addButtonsToTable(this, deleteColumn);
        // We add the data to the table
        categoryTV.setItems(categoriesList);
    }

    /**
     * TODO display all the categories
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // In case we don't have any category
        categoryTV.setPlaceholder(new Label("There is currently no category to display"));
        this.fillTable();
    }
}