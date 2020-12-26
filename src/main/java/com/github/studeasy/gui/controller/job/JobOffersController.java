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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user.getRole() instanceof RoleAdmin){
            label.setText("Job offer pending");
            searchJobTF.setVisible(false);
            searchImage.setOpacity(0);
            try {
                JobList.addAll(FACADE.getPendingJob());
                listview.getItems().setAll(JobList);
                listview.getSelectionModel().selectedItemProperty().addListener(observable -> System.out.printf("Valeur sélectionnée: %d", listview.getSelectionModel().getSelectedItem().getIdJob()).println());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
