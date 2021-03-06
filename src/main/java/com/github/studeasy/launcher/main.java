package com.github.studeasy.launcher;

import com.github.studeasy.dao.notificationDAO.NotificationDAO;
import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.logic.facades.FacadeNotification;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The main class, launching the application
 */
public class main extends Application {

    /**
     * Method coming from the parent class Application
     * Used to start the application and display the login
     * @param stage the container of the view
     * @throws IOException if an error occurs
     */
    @Override
    public void start(Stage stage) throws IOException{
        stage.setTitle("Stud'Easy");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setOnCloseRequest( event -> FacadeNotification.getInstance().stopTimer());
        stage.getIcons().add(new Image("images/logo.png"));
        stage.setScene(new Scene(AbstractRouter.load(AbstractRouter.LOGIN_FXML_PATH)));
        stage.show();
    }

    /**
     * Main method called when we launch the app, it calls the start method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}