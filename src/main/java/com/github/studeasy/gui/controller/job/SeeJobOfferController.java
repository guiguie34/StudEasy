package com.github.studeasy.gui.controller.job;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.JobRouter;
import com.github.studeasy.logic.common.Job;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.common.role.RoleAdmin;
import com.github.studeasy.logic.common.role.RolePartner;
import com.github.studeasy.logic.common.role.RoleStudent;
import com.github.studeasy.logic.facades.FacadeJob;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Displays one job offer (but not update, conflict with addUpdate ?)
 */
public class SeeJobOfferController implements Initializable {

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
     * Job accessed
     */
    private final Job job;

    @FXML
    private Label label;

    @FXML
    private Label titleLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextArea descriptionTA;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Label labelText;



    /**
     * Instantiates the variables
     * @param user The current user
     */
    public SeeJobOfferController(Object job,Object user) {
        this.ROUTER = JobRouter.getInstance();
        this.FACADE = FacadeJob.getInstance();
        this.user = (User)user;
        this.job = (Job)job;

    }

    public void cancel(ActionEvent event) throws IOException {

        ((JobRouter) ROUTER).viewJobs(event);

    }

    public void option1(ActionEvent event){
        if(user.getRole() instanceof RoleAdmin) {
            //reject job
            try {
                ((FacadeJob)FACADE).choiceForJob(job,1);
                labelText.setTextFill(Color.GREEN);
                labelText.setText("Success, job refused !");
                button1.setVisible(false);
                button2.setVisible(false);
            } catch (Exception e) {
                labelText.setTextFill(Color.RED);
                labelText.setText("An error occurs, please retry");
                e.printStackTrace();

            }
        }

        if(user.getRole() instanceof RolePartner) {
            //delete job
            if (AbstractRouter.confirmationBox("Are you sure you want to delete your job offer ?",
                    "Confirmation of the deletion",
                    "Stud'Easy - Confirmation")) {
                try {
                    ((FacadeJob) FACADE).deleteJob(job);
                    labelText.setTextFill(Color.GREEN);
                    labelText.setText("Success, job deleted !");
                    button1.setVisible(false);
                    button2.setVisible(false);
                } catch (Exception e) {
                    labelText.setTextFill(Color.RED);
                    labelText.setText("An error occurs, please retry");

                }
            }
        }

    }

    public void option2(ActionEvent event) throws IOException {
        if(user.getRole() instanceof RoleAdmin) {
            //accept job
            try {
                ((FacadeJob)FACADE).choiceForJob(job,2);
                labelText.setTextFill(Color.GREEN);
                labelText.setText("Success, job accepted !");
                button1.setVisible(false);
                button2.setVisible(false);
            } catch (Exception e) {
                labelText.setTextFill(Color.RED);
                labelText.setText("An error occurs, please retry");
                e.printStackTrace();
            }
        }
        if(user.getRole() instanceof RolePartner) {
            //update job
            ((JobRouter)ROUTER).addOrUpdateJob(event,1,job);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        titleLabel.setText(job.getTitle());
        locationLabel.setText(job.getLocalisation());
        roleLabel.setText(job.getRole());
        startLabel.setText(job.getStart());
        durationLabel.setText(job.getDuration());
        mailLabel.setText(job.getContactMail());
        phoneLabel.setText(job.getContactPhone());
        descriptionTA.setText(job.getDescription());


        if(user.getRole() instanceof RoleAdmin) {
            label.setText("Validate Job offer");
            button1.setVisible(true);
            button2.setVisible(true);
            button1.setText("Reject");
            button2.setText("Accept");
        }
        if(user.getRole() instanceof RoleStudent){
            label.setText("Job offer");

        }
        if(user.getRole() instanceof RolePartner){
            label.setText("My Job offer");
            button1.setVisible(true);
            button2.setVisible(true);
            button1.setText("Delete");
            button2.setText("Update");

        }
    }
}
