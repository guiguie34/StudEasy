package com.github.studeasy.gui.controller.feedback;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.FeedbackRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.logic.common.Feedback;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.facades.FacadeFeedback;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The controller for the feedbacks view of a service
 */
public class SeeFeedbacksServiceController implements Initializable {

    /**
     * The router used by the controller
     */
    protected final AbstractRouter ROUTER;

    /**
     * The service router used by the controller
     */
    protected final AbstractRouter SERVICE_ROUTER;

    /**
     * The facade used by the controller
     */
    protected final FacadeFeedback FACADE;

    /**
     * The field to search a feedback
     */
    @FXML
    private TextField searchFeedbackTF;

    /**
     * The button to add a feedBack
     */
    @FXML
    private Button addFeedbackButton;

    /**
     * The label if an error occur
     */
    @FXML
    private Label failLabel;

    /**
     * The label of the title of the page
     */
    @FXML
    private Label titleLabel;

    /**
     * The table containing the feedbacks information
     */
    @FXML
    private TableView<Feedback> feedbackManagement;

    /**
     * The column containing the titles of the feedbacks
     */
    @FXML
    private TableColumn<Feedback, String> titleColumn;

    /**
     * The column containing the comments of the feedbacks
     */
    @FXML
    private TableColumn<Feedback, String> commentColumn;

    /**
     * The column containing the dates of the feedbacks
     */
    @FXML
    private TableColumn<Feedback, String> dateColumn;

    /**
     * The column containing the rates of the feedbacks
     */
    @FXML
    private TableColumn<Feedback, String> rateColumn;

    /**
     * The column containing the buttons of the feedbacks
     */
    @FXML
    private TableColumn<Feedback, Void> actionColumn;

    /**
     * Used to know from where the user comes from
     * 0 manage pending services / my services
     * 1 see all services online
     */
    private int origin;

    /**
     * Service concerned
     */
    private final Service service;

    /**
     * All the feedbacks displayed in the table
     */
    private ObservableList<Feedback> feedbackList;

    public SeeFeedbacksServiceController(Service service, int origin) {
        this.ROUTER = FeedbackRouter.getInstance();
        this.SERVICE_ROUTER = ServiceRouter.getInstance();
        this.FACADE = FacadeFeedback.getInstance();
        this.origin = origin;
        this.service = service;
    }

    /**
     * Triggered when the user wants to go to add a feedback page
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void loadAddFeedback(ActionEvent event) throws IOException {
        ((FeedbackRouter)ROUTER).addFeedback(FeedbackRouter.ADD_FEEDBACK_FXML_PATH,event,service,origin);
    }

    /**
     * Triggered when the user pushes one of the delete button
     * @param event the event triggered
     * @param feedbackToDelete the feedback the admin wants to delete
     */
    public void deleteFeedback(ActionEvent event, Feedback feedbackToDelete){
        // Display a message to confirm the deletion of the selected category
        if(AbstractRouter.confirmationBox("Are you sure you want to delete this feedback ?",
                "Confirmation of the deletion: "+feedbackToDelete.getTitle(),
                "Stud'Easy - Confirmation")){
            try{
                // We ask the facade to delete the feedback
                FACADE.deleteFeedback(feedbackToDelete);
                // We remove this category from the table
                feedbackList.removeAll(feedbackToDelete);
            }catch (SQLException e){
                e.printStackTrace();
                failLabel.setAlignment(Pos.CENTER);
                failLabel.setTextFill(Paint.valueOf("red"));
                failLabel.setText("Error when deleting in database, try again later");
            }catch (Exception e){
                e.printStackTrace();
                failLabel.setAlignment(Pos.CENTER);
                failLabel.setTextFill(Paint.valueOf("red"));
                failLabel.setText("Error, try again later");
            }

        }
    }

    /**
     * Function used at the initialization, to create and associate the update/delete buttons to the data
     * @param currentController the controller associated, to trigger the events with the buttons
     * @param tabC the column where we add the buttons (update or delete column)
     */
    private void addButtonsToTable(SeeFeedbacksServiceController currentController, TableColumn<Feedback, Void> tabC) {
        // cellFactory will contain our buttons
        Callback<TableColumn<Feedback, Void>, TableCell<Feedback, Void>> cellFactory = new Callback<TableColumn<Feedback, Void>, TableCell<Feedback, Void>>() {
            // For each row of the table view, we want to create the buttons
            @Override
            public TableCell<Feedback, Void> call(final TableColumn<Feedback, Void> param) {
                final TableCell<Feedback, Void> cell = new TableCell<Feedback, Void>() {
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
                        // Delete column
                        if ("actionColumn".equals(tabC.getId())) {
                            img = new Image("images/common/trash.png");
                            btn.setOnAction(event -> currentController.deleteFeedback(event, getTableView().getItems().get(getIndex())));
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
     * Triggered when the user types text in the search text field
     * It filters the data to display only the feedbacks comments matching what is entered
     * @param observable
     * @param oldValue
     * @param newValue the new value entered by the user
     * @param filteredData the data filtered
     */
    public void searchFeedback(ObservableValue<? extends String> observable, String oldValue, String newValue, FilteredList<Feedback> filteredData) {
        // We check for each feedback
        filteredData.setPredicate(feedback -> {
            // If the text field is empty, we display all the feedbacks
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // We compare what he entered (in lower case)
            String lowerCaseFilter = newValue.toLowerCase();
            // If the filter matches the comment of an feedback
            // The feedback is displayed
            // The comment doesn't match, not displayed
            return feedback.getComment().toLowerCase().contains(lowerCaseFilter);
        });
    }

    /**
     * Triggered when the user pushed the cancel button
     * Cancel the action and redirect to the service page
     * @param event
     */
    public void cancel(ActionEvent event) {
        try {
            //Come back to the view of the service
            ((ServiceRouter) SERVICE_ROUTER).viewService(ServiceRouter.VIEW_SERVICE_FXML_PATH,event,service,origin);
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
        // In case we don't have any feedback
        feedbackManagement.setPlaceholder(new Label("There is currently no feedback to display"));
        // We retrieve all the categories and put them in an observable list
        feedbackList = FXCollections.observableArrayList(FACADE.seeAllFeedbacks(service.getIdService()));
        // We put the names of the feedbacks on the right column
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<Feedback,String>("title")
        );
        // Same for the last name
        rateColumn.setCellValueFactory(
                new PropertyValueFactory<Feedback,String>("rate")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<Feedback,String>("date")
        );

        commentColumn.setCellValueFactory(
                new PropertyValueFactory<Feedback,String>("comment")
        );

        if(Session.getInstance().isAdmin()){
            // We add the delete buttons to give to the admin the permission to delete a feedback
            addButtonsToTable(this, actionColumn);
        }


        // We create a filtered list containing the data
        FilteredList<Feedback> filteredFeedbacks = new FilteredList<>(feedbackList, p -> true);
        // We associate the text field with a function listening to what is entered
        searchFeedbackTF.textProperty().addListener((observable,oldValue,newValue) -> this.searchFeedback(observable,oldValue,newValue,filteredFeedbacks));
        // We create a sorted list based on our filtered data
        SortedList<Feedback> sortedData = new SortedList<>(filteredFeedbacks);
        sortedData.comparatorProperty().bind(feedbackManagement.comparatorProperty());
        // We add the sorted data in the table
        feedbackManagement.setItems(sortedData);

        feedbackManagement.setRowFactory( tv -> {
            return new TableRow<Feedback>();
        });

        titleLabel.setText("Feedbacks for the service");

        addFeedbackButton.setVisible(FACADE.hasCommand(service.getIdService()) && Session.getInstance().isStudent());
    }
}
