package com.github.studeasy.gui.routers;

import com.github.studeasy.dao.notificationDAO.NotificationDAO;
import com.github.studeasy.gui.controller.notifications.SeeNotificationController;
import com.github.studeasy.gui.controller.partner.AddUpdatePartnerController;
import com.github.studeasy.logic.common.Notification;
import com.github.studeasy.logic.facades.FacadeNotification;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Router for the notification's routes
 */
public class NotificationRouter extends AbstractRouter{

    /**
     * Singleton of the router notification
     */
    private static NotificationRouter notificationRouter = null;

    /**
     * Constructor of singleton NotificationRouter
     */
    private NotificationRouter() {
        super();
    }

    /**
     * Static function that allow to get the instance of the NotificationRouter
     * @return the instance of FacadeNotification
     */
    public static synchronized NotificationRouter getInstance(){
        if(notificationRouter == null){
            notificationRouter = new NotificationRouter();
        }
        return notificationRouter;
    }

    /**
     * Path to see all the notifications
     */
    private final static String SEE_ALL_NOTIFICATIONS_FXML_PATH = "views/notifications/notificationManagement.fxml";
    /**
     * Path to see one notification
     */
    private final static String SEE_NOTIFICATION_FXML_PATH = "views/notifications/seeNotification.fxml";

    /**
     * Load the view to see all notifications for the current user
     * @param event the event triggering the method
     * @throws IOException if an error occurs
     */
    public void manageNotifications(Event event) throws IOException {
        changeView(SEE_ALL_NOTIFICATIONS_FXML_PATH,event);
    }

    public void seeNotification(Event event, Notification notif) throws IOException{
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(SEE_NOTIFICATION_FXML_PATH));
        // We create the controller with addUpdate telling if we add or update
        SeeNotificationController seeNotificationController = new SeeNotificationController(notif);
        // We link this controller with the FXML
        loader.setController(seeNotificationController);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event, root);

    }


}
