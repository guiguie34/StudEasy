package com.github.studeasy.gui.controller.user;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.gui.controller.AbstractController;
import com.github.studeasy.gui.routers.user.RouterUser;
import com.github.studeasy.logic.facades.user.FacadeUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class LoginController extends AbstractController {

    @FXML
    private TextField emailTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Label loginFailLabel;

    public LoginController(){
        super(new RouterUser(),new FacadeUser());
    }

    public void login(ActionEvent event) throws IOException {
        String email = emailTF.getText();
        String password = passwordTF.getText();
        String loginFail= loginFailLabel.getText();

        // Surrounded by a try catch, in case wrong auth
        try {
            ((FacadeUser) facade).login(email, password);
            ((RouterUser) router).login(event);
        }
        catch(BadCredentialsException e){
            loginFailLabel.setAlignment(Pos.CENTER);
            loginFailLabel.setTextFill(Paint.valueOf("red"));
            loginFailLabel.setText("Bad credentials, please retry");
        }
        catch (Exception e){
            e.printStackTrace();
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