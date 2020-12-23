package com.github.studeasy.gui.controller.partner;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.facades.FacadeUser;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

/**
 * Handles the creation of partner
 */
public class AddUpdatePartnerController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     /**
     * The facade used by the controller
     */
    private final FacadeUser FACADE;

    /**
     * The field to enter the email
     */
    @FXML
    private TextField emailTF;

    /**
     * The field to confirm the email
     */
    @FXML
    private TextField confirmEmailTF;

    /**
     * The field to enter the firstname
     */
    @FXML
    private TextField firstnameTF;

    /**
     * The field to enter the lastname
     */
    @FXML
    private TextField lastnameTF;

    /**
     * The field to enter the password
     */
    @FXML
    private TextField passwordTF;

    /**
     * The field to enter the password
     */
    @FXML
    private TextField confirmPasswordTF;

    /**
     * The field to enter the company name
     */
    @FXML
    private TextField companyTF;

    /**
     * Text label
     */
    @FXML
    private Label label;

    /**
     * Tooltip for password
     */
    @FXML 
    private Tooltip passwordTooltip;

    /**
     * Instantiate the parent's attributes with
     * a router and a facade used for admin controller
     */
    public AddUpdatePartnerController(){
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
    }


    /**
     * Submit the information provided by the admin for create a partner account to the facade
     * @param event The event triggering the function
     */
    public void submit(ActionEvent event) {
        // We retrieve the user inputs
        String email = emailTF.getText();
        String password = passwordTF.getText();
        String confirmEmail = confirmEmailTF.getText();
        String confirmPassword = confirmPasswordTF.getText();
        String firstname = firstnameTF.getText();
        String lastname = lastnameTF.getText();
        String company = companyTF.getText();
        label.setText("");
            // We ask the facade to check
        if(!firstname.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !company.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !confirmEmail.isEmpty()) {
            try {
                FACADE.submitAddPartner(email, confirmEmail, password, confirmPassword, firstname, lastname, company);
                label.setTextFill(Color.GREEN);
                label.setText("Success ! ");
                emailTF.setText("");
                passwordTF.setText("");
                firstnameTF.setText("");
                lastnameTF.setText("");
                companyTF.setText("");
                confirmEmailTF.setText("");
                confirmPasswordTF.setText("");
            } catch (SQLIntegrityConstraintViolationException e) {
                label.setTextFill(Color.RED);
                label.setText("The email address provided already exists in the system, please retry with another email");
            } catch (BadInformationException e) {
                label.setText(e.getMessage());
            } catch (Exception e) {
                label.setText("An error occurs, please retry");
            }
        }
        else{
            label.setTextFill(Color.RED);
            label.setText("Please fill all the field");
        }
    }

    /**
     * Return to the manage Partner view
     * @param event The event triggering the function
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        ((UserRouter)ROUTER).managePartner(event);
    }

    @Override
    /**
     * TODO: Use for update
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
