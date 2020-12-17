package com.github.studeasy.gui.controller.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.facades.FacadeUser;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

/**
 * The controller for the Register/Update a student account view
 */
public class RegisterUpdateController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     /**
     * The facade used by the controller
     */
    private final FacadeUser FACADE;
    private final int action;

    /**
     * The field to enter the first name
     */
    @FXML
    private TextField firstNameTF;

    /**
     * The field to enter the last name
     */
    @FXML
    private TextField lastNameTF;

    /**
     * The field to enter the email
     */
    @FXML
    private TextField emailTF;

    /**
     * The field to enter the pseudo
     */
    @FXML
    private TextField pseudoTF;

    /**
     * The field to enter the email confirmation
     */
    @FXML
    private TextField confirmEmailTF;

    /**
     * The field to enter the password
     */
    @FXML
    private TextField passwordTF;

    /**
     * The field to enter the password confirmation
     */
    @FXML
    private TextField confirmPasswordTF;

    /**
     * Label only visible when an error occurs
     */
    @FXML
    private Label registerFailLabel;

    /**
     * The button for register
     */
    @FXML
    private Button registerB;

    /**
     * The button for update an user
     */
    @FXML
    private Button updateB;

    /**
     * Instantiate the parent's attributes with
     * a router and a facade used for users
     */
    public RegisterUpdateController(int action){
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
        this.action = action;
    }


    /**
     * Triggered when the user pushed the register button
     * Register a User on the platform and redirect him, or indicates him its credentials are wrong
     * @param event
     */
    public void register(){

        // We retrieve the user inputs
        String firstName = firstNameTF.getText();
        String lastName = lastNameTF.getText();
        String email = emailTF.getText();
        String confirmEmail = confirmEmailTF.getText();
        String password = passwordTF.getText();
        String confirmPassword = confirmPasswordTF.getText();
        String pseudo = pseudoTF.getText();
        //If all the fields aren't empty
        if(!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !pseudo.isEmpty()){
            try {
                FACADE.register(firstName,lastName,pseudo,email,confirmEmail,password,confirmPassword);
                ROUTER.changeView(UserRouter.LOGIN_FXML_PATH,event);


            }catch (BadInformationException exception){
                registerFailLabel.setAlignment(Pos.CENTER);
                registerFailLabel.setTextFill(Paint.valueOf("red"));
                registerFailLabel.setText(exception.getMessage());

            }
            catch (SQLIntegrityConstraintViolationException e){
                registerFailLabel.setAlignment(Pos.CENTER);
                registerFailLabel.setTextFill(Paint.valueOf("red"));
                registerFailLabel.setText("There is already an account with this email");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }else{
            registerFailLabel.setAlignment(Pos.CENTER);
            registerFailLabel.setTextFill(Paint.valueOf("red"));
            registerFailLabel.setText("All fields are required");
        }






    }

    /**
     * Triggered when the user pushed the update button
     * Update the parameters of a user and redirect him, or indicates him its credentials are wrong
     * @param event
     */
    public void update(){

    }

    /**
     * Triggered when the user pushed the cancel button
     * Cancel the action and redirect to the home of the user
     * @param event
     */
    public void cancel(ActionEvent event) throws IOException {
        ROUTER.changeView(UserRouter.LOGIN_FXML_PATH,event);
    }

    /**
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //if register
        if(action == 0){
            registerB.setText("Register");
            registerB.setOnAction((event -> register()));
        }else if(action == 1){
            registerB.setText("Update");
            registerB.setOnAction((event -> update()));
        }
    }
}
