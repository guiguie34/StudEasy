package com.github.studeasy.gui.controller.user;

import com.github.studeasy.gui.controller.AbstractController;
import com.github.studeasy.gui.routers.user.RouterUser;
import com.github.studeasy.logic.facades.user.FacadeUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends AbstractController {

    @FXML
    private TextField emailTF;

    @FXML
    private TextField passwordTF;

    public LoginController(){
        super(new RouterUser(),new FacadeUser());
    }

    public void login(ActionEvent event) throws IOException {
        String email = emailTF.getText();
        String password = passwordTF.getText();

        // Surrounded by a try catch, in case wrong auth
        ((FacadeUser) facade).login(email, password);
        ((RouterUser) router).login(event);
    }

    public void loadRegister(ActionEvent event) throws IOException {
        ((RouterUser) router).register(event);
    }

    /**
     * Called when the user presses the "exit" button
     * Shut down the application
     * @param event, the action trigerring this method
     */
    public void exit(ActionEvent event) {
        // Shut down the JVM
        Platform.exit();
        // Terminate the application
        System.exit(0);
    }
}