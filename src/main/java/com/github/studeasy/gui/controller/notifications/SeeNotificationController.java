package com.github.studeasy.gui.controller.notifications;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.NotificationRouter;
import com.github.studeasy.logic.common.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller to display the notification
 */
public class SeeNotificationController implements Initializable {

    /**
     * Notification to display
     */
    private final Notification notification;

    /**
     * Router for notifications
     */
    private final AbstractRouter ROUTER;

    /**
     * Label to display notification title
     */
    @FXML
    private Label titleLabel;

    /**
     * TextArea to display notification text
     */
    @FXML
    private TextArea notifTA;


    /**
     * Instantiates the notification
     * @param notification Notification to displays
     */
    public SeeNotificationController(Notification notification){
        this.ROUTER = NotificationRouter.getInstance();
        this.notification= notification;
    }

    public void cancel(ActionEvent event) throws IOException {
        ((NotificationRouter)ROUTER).manageNotifications(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(notification.getTitle());
        notifTA.setText(notification.getDescription());
    }
}
