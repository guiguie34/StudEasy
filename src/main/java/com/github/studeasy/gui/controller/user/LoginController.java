package com.github.studeasy.gui.controller.user;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.facades.user.FacadeUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the login view
 */
public class LoginController implements Initializable {

    /**
     * The router used by the controller
     */
    protected AbstractRouter router;
    /**
     /**
     * The facade used by the controller
     */
    protected FacadeUser facade;

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
        this.router = UserRouter.getInstance();
        this.facade = FacadeUser.getInstance();
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
            facade.login(email, password);
            // If it's alright, we're redirected to the right page by the router
            ((UserRouter)router).login(event);
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
        router.changeView(UserRouter.REGISTER_FXML_PATH,event);
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

    /**
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}