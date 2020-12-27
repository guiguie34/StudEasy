package com.github.studeasy.gui.controller.job;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.JobRouter;
import com.github.studeasy.logic.common.Job;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.common.role.RoleAdmin;
import com.github.studeasy.logic.facades.FacadeJob;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import javax.swing.*;
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
        this.FACADE = FacadeJob.getInstance();
        this.user = (User)user;
    }

    /**
     * Load the view to return to the homepage
     * @param event The event triggering the method
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        ((JobRouter)ROUTER).backToDashboard(event);
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
        private final ImageView pending = new ImageView("images/service/pending.png");


        public JobListCell(){

            /*
            Handling button click on each cells
             */
            buttonSee.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Action: "+getItem().getIdJob());
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


            pending.setFitWidth(20);
            pending.setFitHeight(20);
            buttonSee.setId("neutralButton");
            GridPane.setConstraints(titleLabel, 0, 0);
            GridPane.setConstraints(buttonSee, 2, 0);
            GridPane.setConstraints(text, 0, 1,3,1);
            GridPane.setConstraints(pending,1,0);
            gridPane.setHgap(4);
            gridPane.setVgap(4);
            gridPane.getChildren().setAll(titleLabel,buttonSee,text,pending);
            content.getChildren().add(gridPane);
        }


        @Override
        protected void updateItem(Job item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (!empty && item != null) {
                titleLabel.setText(item.getTitle());
                text.setText(item.getDescription());
                //Display
                setGraphic(content);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user.getRole() instanceof RoleAdmin){
            label.setText("Job offer pending");
            searchJobTF.setVisible(false);
            searchImage.setOpacity(0);
            try {
                JobList.addAll(FACADE.getPendingJob());
                listview.getItems().setAll(JobList);
                listview.setCellFactory(lv -> new JobListCell());
                listview.getSelectionModel().selectedItemProperty().addListener(observable -> System.out.printf("Valeur sélectionnée: %d", listview.getSelectionModel().getSelectedItem().getIdJob()).println());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
