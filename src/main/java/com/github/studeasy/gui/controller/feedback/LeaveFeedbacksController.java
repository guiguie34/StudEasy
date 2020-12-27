package com.github.studeasy.gui.controller.feedback;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.FeedbackRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.facades.FacadeFeedback;
import com.github.studeasy.logic.facades.FacadeUser;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

/**
 * The controller for the "leave a feedback of a service" view
 */
public class LeaveFeedbacksController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     /**
     * The facade used by the controller
     */
    private final FacadeFeedback FACADE;

    /**
     * The field to enter the title
     */
    @FXML
    private TextField titleTF;

    /**
     * The area to enter the comment
     */
    @FXML
    private TextArea commentTA;

    /**
     * The label if an error occur
     */
    @FXML
    private Label failLabel;


    /**
     * The Choice box for the rate
     */
    @FXML
    private ChoiceBox<Integer> rateCB;

    /**
     * id of the service concerned
     */
    private int idService;

    public LeaveFeedbacksController(int idService) {
        this.ROUTER = FeedbackRouter.getInstance();
        this.FACADE = FacadeFeedback.getInstance();
        this.idService = idService;
    }

    public void leaveFeedback(ActionEvent event){
        String title = titleTF.getText();
        String comment = commentTA.getText();
        int rate = rateCB.getValue();

        if(!title.isEmpty() && !comment.isEmpty()){
            try {
                if(AbstractRouter.confirmationBox("You will leave this feedback","Confirm your action","Stud'Easy confirmation")) {
                    FACADE.leaveFeedback(title, comment, rate, idService);
                    ((FeedbackRouter) ROUTER).viewFeedbacks(FeedbackRouter.FEEDBACKS_SERVICE_FXML_PATH, event, idService);
                }

            }catch (BadInformationException exception){
                failLabel.setAlignment(Pos.CENTER);
                failLabel.setTextFill(Paint.valueOf("red"));
                failLabel.setText(exception.getMessage());

            }
            catch (SQLIntegrityConstraintViolationException e){
                failLabel.setAlignment(Pos.CENTER);
                failLabel.setTextFill(Paint.valueOf("red"));
                failLabel.setText("There is already an account with this email");
            }
            catch (Exception e){
                e.printStackTrace();
                failLabel.setAlignment(Pos.CENTER);
                failLabel.setTextFill(Paint.valueOf("red"));
                failLabel.setText("Error, Try again later");
            }

        }else{
            failLabel.setAlignment(Pos.CENTER);
            failLabel.setTextFill(Paint.valueOf("red"));
            failLabel.setText("All fields are required");
        }


    }

    /**
     * Triggered when the user pushed the cancel button
     * Cancel the action and redirect to the feedbacks page
     * @param event
     */
    public void cancel(ActionEvent event) {
        try {
            //Come back to the view of the feedbacks
            ((FeedbackRouter) ROUTER).viewFeedbacks(FeedbackRouter.FEEDBACKS_SERVICE_FXML_PATH, event, idService);
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
        rateCB.getItems().add(1);
        rateCB.getItems().add(2);
        rateCB.getItems().add(3);
        rateCB.getItems().add(4);
        rateCB.getItems().add(5);
        rateCB.setValue(1);

    }
}
