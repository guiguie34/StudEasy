package com.github.studeasy.gui.controller.notifications;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.NotificationRouter;
import com.github.studeasy.logic.facades.FacadeNotification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ButtonNotificationController implements Initializable {


    /**
     * Router for notifications
     */
    private final AbstractRouter ROUTER;

    /**
     * Facade for notifications
     */
    private final FacadeNotification FACADE;
    /**
     * Displays the number of notifications
     */
    @FXML
    private Label nbNotif;

    /**
     * Displays if the number of notifications is over 0
     */
    @FXML
    private Circle circleRed;



    public ButtonNotificationController(){
        this.ROUTER = NotificationRouter.getInstance();
        this.FACADE = FacadeNotification.getInstance();
    }


    /**
     * Handle the click
     * @param event the event triggering the method
     */
    public void clickNotif(MouseEvent event) throws IOException {

        ((NotificationRouter)ROUTER).manageNotifications(event);
    }

    /**
     * Set number of notifications
     * @param value number of notifications
     */
    public void setValueLabel(int value){
        if(value == 0){
            circleRed.setVisible(false);
            nbNotif.setVisible(false);
        }
        else{
            circleRed.setVisible(true);
            nbNotif.setVisible(true);
        }
        nbNotif.setText(String.valueOf(value));
    }


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FACADE.getNumberNotification(this);
        } catch (Exception e) {
            setValueLabel(0);
        }
    }
}
