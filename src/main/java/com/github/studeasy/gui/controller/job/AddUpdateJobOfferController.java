package com.github.studeasy.gui.controller.job;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.JobRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Job;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeJob;
import com.github.studeasy.logic.facades.FacadeUser;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Handles the add of job offer
 */
public class AddUpdateJobOfferController implements Initializable {


    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;


    /**
     * The facade used by the controller
     */
    private final FacadeJob FACADE;

    /**
     * Indicates if we want to add or update
     * 0 add,
     * 1 update
     */
    private final int addUpdate;

    /**
     * The job we try to update
     * null if we try to add
     */
    private Job job;

    /**
     * Contains title of the job offer
     */
    @FXML
    private TextField titleTF;

    /**
     * Contains location of the job offer
     */
    @FXML
    private TextField locationTF;

    /**
     * Contains role of the job offer
     */
    @FXML
    private TextField roleTF;

    /**
     * Contains duration of the job offer
     */
    @FXML
    private TextField durationTF;

    /**
     * Contains mail contact of the job offer
     */
    @FXML
    private TextField mailTF;

    /**
     * Contains phone contact of the job offer
     */
    @FXML
    private TextField phoneTF;

    /**
     * Contains start date of the job offer
     */
    @FXML
    private DatePicker dateTF;

    /**
     * Contains description of job offer
     */
    @FXML
    private TextArea descriptionTA;

    /**
     * Label to display the result of the submit operation
     */
    @FXML
    private Label label;

    /**
     * Button to submit
     */
    @FXML
    private Button buttonSubmit;

    /**
     * Page title
     */
    @FXML
    private Label titleLabel;


    /**
     * Instantiate the parent's attributes with
     * a router and a facade used for partner controller
     */
    public AddUpdateJobOfferController(int addUpdate, Object jobToUpdate){
        this.ROUTER = JobRouter.getInstance();
        this.FACADE = FacadeJob.getInstance();
        this.addUpdate = addUpdate;
        this.job = (Job)jobToUpdate;
    }

    /**
     * Submit the information provided by the partner to add or update a job
     * @param event
     */
    public void submit(ActionEvent event){
        String title = titleTF.getText();
        String location = locationTF.getText();
        String role = roleTF.getText();
        String duration = durationTF.getText();
        String mail = mailTF.getText();
        String phone = phoneTF.getText();
        LocalDate localDate = dateTF.getValue();
        String description = descriptionTA.getText();
        if(addUpdate ==0){
            if(!title.isEmpty() && !location.isEmpty() && !role.isEmpty() && !duration.isEmpty() && !mail.isEmpty() && !phone.isEmpty() && localDate !=null && !description.isEmpty()){
                try{
                    FACADE.addJob(title,location,role,duration,mail,phone,localDate,description);
                    label.setTextFill(Color.GREEN);
                    label.setText("Success ! ");
                    titleTF.setText("");
                    locationTF.setText("");
                    roleTF.setText("");
                    durationTF.setText("");
                    mailTF.setText("");
                    phoneTF.setText("");
                    dateTF.getEditor().clear();
                    descriptionTA.setText("");
                } catch (BadInformationException e) {
                    label.setTextFill(Color.RED);
                    label.setText(e.getMessage());
                } catch (Exception e) {
                    label.setTextFill(Color.RED);
                    label.setText("An error occurs, please retry");
                }
            }
            else{
                label.setTextFill(Color.RED);
                label.setText("Please fill all the field");
            }

        }
        else {
            if (!title.isEmpty() && !location.isEmpty() && !role.isEmpty() && !duration.isEmpty() && !mail.isEmpty() && !phone.isEmpty() && localDate != null && !description.isEmpty()) {

                if (AbstractRouter.confirmationBox("Are you sure you want to update your job offer ? \nIf your job offer have been already validated by an admin, \nyour job offer will have to be validated once again",
                        "Confirmation of the deletion",
                        "Stud'Easy - Confirmation")) {
                    try {
                        FACADE.updateJob(title, location, role, duration, mail, phone, localDate, description, job.getIdJob());
                        label.setTextFill(Color.GREEN);
                        label.setText("Success ! You can click on 'back' button ");

                    }
                    catch (BadInformationException e) {
                        label.setTextFill(Color.RED);
                        label.setText(e.getMessage());
                    } catch (Exception e) {
                        label.setTextFill(Color.RED);
                        label.setText("An error occurs, please retry");
                    }
                }
            }
            else{
                label.setTextFill(Color.RED);
                label.setText("Please fill all the field");
            }
        }
    }

    /**
     * Load the view to return to the homepage
     * @param event The event triggering the method
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        if(addUpdate == 0) {
            ((JobRouter) ROUTER).backToDashboard(event);
        }
        if(addUpdate == 1){
            ((JobRouter)ROUTER).viewJobs(event);

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.addUpdate == 1){
            titleLabel.setText("Update job");
            buttonSubmit.setText("Update");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(job.getStart(), formatter);
            titleTF.setText(job.getTitle());
            locationTF.setText(job.getLocalisation());
            roleTF.setText(job.getRole());
            durationTF.setText(job.getDuration());
            mailTF.setText(job.getContactMail());
            phoneTF.setText(job.getContactPhone());
            dateTF.setValue(localDate);
            descriptionTA.setText(job.getDescription());
        }
    }
}
