package com.github.studeasy.gui.controller.job;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.JobRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Job;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.common.role.RoleAdmin;
import com.github.studeasy.logic.common.role.RolePartner;
import com.github.studeasy.logic.common.role.RoleStudent;
import com.github.studeasy.logic.facades.FacadeJob;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Displays all the job offer
 */
public class JobOffersController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;


    /**
     * The facade used by the controller
     */
    private final FacadeJob FACADE;

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER_USER;

    /**
     * User connected
     */
    private final User user;

    /**
     * Label to display the title of the view
     */
    @FXML
    private Label label;

    /**
     * TextField to search
     */
    @FXML
    private TextField searchJobTF;

    @FXML
    private ImageView searchImage;

    /**
     * Contains the list of job
     */
    @FXML
    private ListView<Job> listview;

    /**
     * All the job
     */
    private ObservableList<Job> JobList= FXCollections.observableArrayList();


    /**
     * Instantiates the variables
     * @param user The current user
     */
    public JobOffersController(Object user) {
        this.ROUTER = JobRouter.getInstance();
        this.ROUTER_USER = UserRouter.getInstance();
        this.FACADE = FacadeJob.getInstance();
        this.user = (User)user;
    }

    /**
     * Load the view to return to the homepage
     * @param event The event triggering the method
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        ((UserRouter)ROUTER_USER).backToDashboard(event);
    }


    /**
     * Allows to make custom cells for job
     */
    private class JobListCell extends ListCell<Job> {

        private final Label titleLabel = new Label();
        private final Button buttonSee = new Button("See Job");
        private final Text text = new Text();
        private final AnchorPane content = new AnchorPane();
        private final GridPane gridPane = new GridPane();
        private final ImageView status = new ImageView("images/service/pending.png");


        public JobListCell(){

            /*
            Handling button click on each cells
             */
            buttonSee.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //System.out.println("Action: "+getItem().getIdJob());
                    try {
                        ((JobRouter)ROUTER).seeJob(event,getItem());
                    } catch (IOException e) {
                        label.setText("Can't load the job, please retry later");
                    }
                }
            });

            /*
             Set style for title and description
             */
            titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.5em; -fx-font-family: Arial");
            text.setStyle("-fx-font-family: Arial");

            status.setFitWidth(20);
            status.setFitHeight(20);

            buttonSee.setId("neutralButton");
            gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, VPos.CENTER, true));
            GridPane.setConstraints(titleLabel, 0, 0);
            GridPane.setConstraints(buttonSee, 2, 0);
            GridPane.setConstraints(text, 0, 1,3,1);
            GridPane.setConstraints(status,1,0);
            AnchorPane.setTopAnchor(gridPane, 0d);
            AnchorPane.setLeftAnchor(gridPane, 0d);
            AnchorPane.setBottomAnchor(gridPane, 0d);
            AnchorPane.setRightAnchor(gridPane, 0d);
            gridPane.setHgap(4);
            gridPane.setVgap(4);
            gridPane.getChildren().setAll(titleLabel,buttonSee,text,status);
            content.getChildren().add(gridPane);
        }


        @Override
        protected void updateItem(Job item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (!empty && item != null) {
                titleLabel.setText(item.getTitle());
                text.setText(item.getDescription());
                status.setVisible(true);
                buttonSee.setVisible(true);
                if(item.getStatus().equals("validated")) {
                    status.setImage(new Image("images/service/validated.png"));
                }
                if(item.getStatus().equals("pending")) {
                    status.setImage(new Image("images/service/pending.png"));
                }
                if(item.getStatus().equals("refused")){
                    status.setImage(new Image("images/service/refused.png"));
                }

                //Display
                setGraphic(content);
            }
            else{
                titleLabel.setText("");
                text.setText("");
                buttonSee.setVisible(false);
                setVisible(false);
                status.setVisible(false);
                //setStyle("-fx-background-color: #F1F3F2;");
                setGraphic(content);

            }
        }
    }

    /**
     * Method to search a job by his title
     * @param observable
     * @param oldValue
     * @param newValue
     * @param filteredData
     */
    public void searchJob(ObservableValue<? extends String> observable, String oldValue, String newValue, FilteredList<Job> filteredData) {
        filteredData.setPredicate(job -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (job.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                // The job is displayed
                return true;
            } else {
                // The name doesn't match, not displayed
                return false;
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user.getRole() instanceof RoleAdmin){
            label.setText("Job offer pending");
            searchJobTF.setVisible(false);
            searchImage.setOpacity(0);
            listview.setPlaceholder(new Label("No pending job available, please retry later"));
            try {
                JobList.addAll(FACADE.getPendingJob());
                listview.getItems().setAll(JobList);
                listview.setCellFactory(lv -> new JobListCell());
                //listview.getSelectionModel().selectedItemProperty().addListener(observable -> System.out.printf("Valeur sélectionnée: %d", listview.getSelectionModel().getSelectedItem().getIdJob()).println());
            } catch (Exception e) {
                listview.setPlaceholder(new Label("An error occurs, please retry later"));
            }
        }
        if(user.getRole() instanceof RoleStudent) {
            label.setText("Job offers");
            listview.setPlaceholder(new Label("No job offer available, please retry later"));
            try{
                JobList.addAll(FACADE.getAllJobs());
                listview.getItems().setAll(JobList);
                listview.setCellFactory(lv -> new JobListCell());
                //listview.getSelectionModel().selectedItemProperty().addListener(observable -> System.out.printf("Valeur sélectionnée: %d", listview.getSelectionModel().getSelectedItem().getIdJob()).println());
                FilteredList<Job> filteredJob = new FilteredList<>(JobList, j -> true);
                searchJobTF.textProperty().addListener((observable,oldValue,newValue) -> this.searchJob(observable,oldValue,newValue,filteredJob));
                SortedList<Job> sortedData = new SortedList<>(filteredJob);
                listview.setItems(sortedData);
            }
            catch (Exception e){
                listview.setPlaceholder(new Label("An error occurs, please retry later"));
            }


        }

        if(user.getRole() instanceof RolePartner) {
            label.setText("My Job offers");
            listview.setPlaceholder(new Label("You don't have job offer, you can add one from your dashboard"));
            try{
                JobList.addAll(FACADE.getMyJobs(user));
                listview.getItems().setAll(JobList);
                listview.setCellFactory(lv -> new JobListCell());
                //listview.getSelectionModel().selectedItemProperty().addListener(observable -> System.out.printf("Valeur sélectionnée: %d", listview.getSelectionModel().getSelectedItem().getIdJob()).println());
                FilteredList<Job> filteredJob = new FilteredList<>(JobList, j -> true);
                searchJobTF.textProperty().addListener((observable,oldValue,newValue) -> this.searchJob(observable,oldValue,newValue,filteredJob));
                SortedList<Job> sortedData = new SortedList<>(filteredJob);
                listview.setItems(sortedData);
            }
            catch (Exception e){
                listview.setPlaceholder(new Label("An error occurs, please retry later"));
            }


        }
    }
}
