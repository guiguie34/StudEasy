package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.notificationDAO.NotificationDAO;
import com.github.studeasy.gui.controller.notifications.ButtonNotificationController;
import com.github.studeasy.logic.common.Notification;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Facade Notification for the NotificationDAO
 * It contains methods that allow to add,display,update notifications
 */
public class FacadeNotification {

    /**
     * Singleton of the facade user
     */
    private static FacadeNotification facadeNotification = null;

    /**
     * The DAO connected to the database
     */
    private final NotificationDAO DAO;

    /**
     * Timer for the thread
     */
    private Timer timer;

    /**
     * Constructor of singleton FacadeNotification
     * Instantiate the factory
     */
    private FacadeNotification() {
        // We retrieve the NotificationDao
        this.DAO = NotificationDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeNotification
     * @return the instance of FacadeNotification
     */
    public static synchronized FacadeNotification getInstance(){
        if(facadeNotification == null){
            facadeNotification = new FacadeNotification();
        }
        return facadeNotification;
    }

    public void getNumberNotification(ButtonNotificationController instance) throws Exception {
        Session session = Session.getInstance();
        User currentUser = session.getCurrentUser();


        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                try {
                    Platform.runLater(() -> {
                        int i = 0;
                        try {
                            i = DAO.getNbNotif(currentUser);
                            instance.setValueLabel(i);
                        }
                        catch (Exception e) {
                            instance.setValueLabel(0);
                        }
                    });


                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        timer.schedule(task, 0, 2 * 1000);
    }

    public void stopTimer(){
        timer.cancel();
    }
    public void launchTimer(){
        timer = new Timer();
    }

    /**
     * Get all the notification for the current user from the DAO
     * @return arraylist of notifications for the current user
     * @throws Exception if an error occurs
     */
    public ArrayList<Notification> getNotification() throws Exception{
        User currentUser  = Session.getInstance().getCurrentUser();
        return DAO.getNotification(currentUser);
    }

    /**
     * Call the DAO to delete the notification
     * @param idNotif id to delete
     * @throws Exception if an error occurs
     */
    public void deleteNotification(int idNotif) throws Exception {
        DAO.deleteNotification(idNotif);
    }

    /**
     * Call the DAO to mark as read the notification
     * @param id id to mark as read
     * @throws Exception if an error occurs
     */
    public void markAsRead(int idNotif) throws Exception{
        DAO.markAsRead(idNotif);
    }

    /**
     * Call the DAO to create a notification
     * @param idOwner owner of the notification
     * @param title title of notification
     * @param description description of the notification
     * @throws Exception if an error occurs
     */
    public void createNotification(int idOwner, String title, String description) throws Exception{
        DAO.createNotification(idOwner, title, description);
    }
}
