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

/**
 * The controller for the login view
 */
public class LoginController extends AbstractController {

    /**
     * The field to enter the email
     */
    @FXML
    private TextField emailTF;

    /**
     * The field to enter the password
     */
    @FXML
    private TextField passwordTF;

    /**
     * Label only visible when an error occurs
     */
    @FXML
    private Label loginFailLabel;

    /**
     * Instantiate the parent's attributes with
     * a router and a facade used for users
     */
    public LoginController(){
        super(new RouterUser(),FacadeUser.getInstance());
    }

    /**
     * Triggered when the user pushed the login button
     * Log the user into the app if its credentials are correct and then
     * redirect him, or indicates him its credentials are wrong.
     * @param event, the event triggered
     */
    public void login(ActionEvent event){
        // We retrieve the user inputs
        String email = emailTF.getText();
        String password = passwordTF.getText();

        // Surrounded by a try catch, in case a wrong auth occurs
        try {
            // We ask the facade to check
            ((FacadeUser) facade).login(email, password);
            // If it's alright, we're redirected to the right page by the router
            ((RouterUser) router).login(event);
        }
        catch(BadCredentialsException e){
            // Wrong credentials, we show the user
            loginFailLabel.setAlignment(Pos.CENTER);
            loginFailLabel.setTextFill(Paint.valueOf("red"));
            loginFailLabel.setText("Bad credentials, please retry");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user wants to go to the register page
     * @param event, the event triggered
     * @throws IOException, if an error occurs
     */
    public void loadRegister(ActionEvent event) throws IOException {
        ((RouterUser) router).register(event);
    }

    /**
     * Called when the user presses the "exit" button
     * Shut down the application
     * @param event, the action trigerring this method
     */
    public void exit(ActionEvent event) {
        // Shuts down the JVM
        Platform.exit();
        // Terminates the application
        System.exit(0);
    }
}