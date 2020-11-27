package com.github.studeasy.launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;


public class main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        Parent p= loader.load(main.class.getClassLoader().getResourceAsStream("Views/login.fxml"));
        stage.setScene(new Scene(p));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}