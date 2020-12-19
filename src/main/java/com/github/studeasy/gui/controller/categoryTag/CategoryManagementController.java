package com.github.studeasy.gui.controller.categoryTag;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CategoryRouter;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.facades.FacadeCategory;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
     * All the categories displayed in the table
     */
    private ObservableList<CategoryTag> categoriesList;

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
        ((CategoryRouter)ROUTER).addOrUpdateCategory(CategoryRouter.ADD_UPDATE_CATEGORY_FXML_PATH,event,0,null);
    }

    /**
     * Triggered when the user pushes one of the update button
     * @param event the event triggered
     * @param categoryToUpdate the category the user wants to update
     */
    public void updateCategory(ActionEvent event, CategoryTag categoryToUpdate) {
        try {
            ((CategoryRouter)ROUTER).addOrUpdateCategory(CategoryRouter.ADD_UPDATE_CATEGORY_FXML_PATH,event,1, categoryToUpdate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the admin types text in the search text field
     * It filters the data to display only the categories matching what is entered
     * @param observable
     * @param oldValue
     * @param newValue the new value entered by the administrator
     * @param filteredData the data filtered
     */
    public void searchCategory(ObservableValue<? extends String> observable, String oldValue, String newValue, FilteredList<CategoryTag> filteredData) {
        // We check for each category
        filteredData.setPredicate(category -> {
            // If the text field is empty, we display all the categories
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // We compare what he entered (in lower case)
            String lowerCaseFilter = newValue.toLowerCase();
            // If the filter matches the name of the category
            if (category.getName().toLowerCase().contains(lowerCaseFilter)) {
                // The category is displayed
                return true;
            } else {
                // The name doesn't match, not displayed
                return false;
            }
        });
    }

    /**
     * Triggered when the user pushes one of the update button
     * @param event the event triggered
     * @param categoryToDelete the category the user wants to update
     */
    public void deleteCategory(ActionEvent event, CategoryTag categoryToDelete){
        // Display a message to confirm the deletion of the selected category
        if(AbstractRouter.confirmationBox("Are you sure you want to delete this category ?",
                "Confirmation of the deletion: "+categoryToDelete.getName(),
                "Stud'Easy - Confirmation")){
            // We ask the facade to delete the category
            //FACADE.deleteCategory(categoryToDelete);
            // We remove this category from the table
            categoriesList.removeAll(categoryToDelete);
        }
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
     * Function from the interface Initializable
     * Retrieve the categories to display them
     * in a table with buttons allowing to update or delete them
     * We also allow the search of categories here
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // In case we don't have any category
        categoryTV.setPlaceholder(new Label("There is currently no category to display"));
        // We retrieve all the categories and put them in an observable list
        //categoriesList = FXCollections.observableArrayList(FACADE.seeAllCategories());
        categoriesList = FXCollections.observableArrayList(
                new CategoryTag("AAA","BBB"),
                new CategoryTag("RGD","BRRBB"),
                new CategoryTag("AAHTGFA","BGBB"),
                new CategoryTag("AARFDA","BRFBB"),
                new CategoryTag("FFFF","BBBBGF"),
                new CategoryTag("AAAFRD","BBGB")
        );
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
        // We create a filtered list containing the data
        FilteredList<CategoryTag> filteredCategories = new FilteredList<>(categoriesList, p -> true);
        // We associate the text field with a function listening to what is entered
        searchCategoryTF.textProperty().addListener((observable,oldValue,newValue) -> this.searchCategory(observable,oldValue,newValue,filteredCategories));
        // We create a sorted list based on our filtered data
        SortedList<CategoryTag> sortedData = new SortedList<>(filteredCategories);
        sortedData.comparatorProperty().bind(categoryTV.comparatorProperty());
        // We add the sorted data in the table
        categoryTV.setItems(sortedData);
    }
}