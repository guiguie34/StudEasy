package com.github.studeasy.gui.controller.home;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.FeedbackRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller of the student view
 */
public class HomeStudentController extends HomeAbstractController implements Initializable {

    public HomeStudentController(){
        super();
    }

    /**
     * Triggered when the user wants to go to his profile page
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void loadMyProfile(ActionEvent event){
        try {
            ((UserRouter)ROUTER).profileUser(event);

        }catch (Exception e){
            e.printStackTrace();
            failLabel.setAlignment(Pos.CENTER);
            failLabel.setTextFill(Paint.valueOf("red"));
            failLabel.setText("Error, try again later");
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
    public void initialize(URL location, ResourceBundle resources) {}
}
