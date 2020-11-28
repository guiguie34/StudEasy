package com.github.studeasy.launcher;

import com.github.studeasy.gui.views.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 *
 */
public class main extends Application {

    /**
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException{
        stage.setTitle("Stud'Easy");
        stage.centerOnScreen();
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(new Scene(new LoginView()));
        stage.show();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}