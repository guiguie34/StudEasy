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

    public void login(ActionEvent av) throws IOException {
        String email = emailTF.getText();
        String password = passwordTF.getText();
        ((FacadeUser) facade).login(email,password);

        if(((FacadeUser) facade).isStudent()){
            ((RouterUser) router).homeStudent(av);
        }
        else if(((FacadeUser) facade).isAdmin()){
            ((RouterUser) router).homeAdmin(av);
        }
        else if (((FacadeUser) facade).isPartner()){
            ((RouterUser) router).homePartner(av);
        }

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