package com.github.studeasy.gui.controller.partner;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.common.role.RolePartner;
import com.github.studeasy.logic.facades.FacadeUser;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
     * Title label
     */
    @FXML
    private Label titleLabel;

    /**
     * Tooltip for password
     */
    @FXML 
    private Tooltip passwordTooltip;

    /**
     * Indicates if we want to add or update
     * 0 add,
     * 1 update
     */
    private int addUpdate;

    /**
     * The partner we try to update
     * null if we try to add
     */
    private User partner;

    /**
     * Instantiate the parent's attributes with
     * a router and a facade used for admin controller
     */
    public AddUpdatePartnerController(int addUpdate, Object partnerToUpdate){
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
        this.addUpdate = addUpdate;
        this.partner = (User)partnerToUpdate;
    }


    /**
     * Submit the information provided by the admin for create or update a partner account to the facade
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
        if(this.addUpdate == 0) {
            // We ask the facade to check
            if (!firstname.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !company.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !confirmEmail.isEmpty()) {
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
                    label.setTextFill(Color.RED);
                    label.setText(e.getMessage());
                } catch (Exception e) {
                    label.setTextFill(Color.RED);
                    label.setText("An error occurs, please retry");
                }
            } else {
                label.setTextFill(Color.RED);
                label.setText("Please fill all the field");
            }
        }
        else{
            if (!firstname.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !company.isEmpty()  && !confirmEmail.isEmpty()) {
                try {
                    if (AbstractRouter.confirmationBox("Are you sure you want to update this partner ?",
                            "Confirmation of the update: " + partner.getFirstname() + " " + partner.getLastname(),
                            "Stud'Easy - Confirmation")) {
                        FACADE.submitUpdatePartner(email, confirmEmail, password, confirmPassword, firstname, lastname, company, partner);
                        label.setTextFill(Color.GREEN);
                        label.setText("Success ! ");
                    }
                }
                catch (SQLIntegrityConstraintViolationException e) {
                    label.setTextFill(Color.RED);
                    label.setText("The email address provided already exists in the system, please retry with another email");
                } catch (BadInformationException e) {
                    label.setTextFill(Color.RED);
                    label.setText(e.getMessage());
                } catch (Exception e) {
                    label.setTextFill(Color.RED);
                    label.setText("An error occurs, please retry");
                }

            }
            else {
                label.setTextFill(Color.RED);
                label.setText("Please fill all the field (except password if not needed)");
            }
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

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.addUpdate == 1){
            // We want to update
            this.titleLabel.setFont(new Font(25));
            this.titleLabel.setText("Update a Partner: "+partner.getFirstname() + " " + partner.getLastname());

            firstnameTF.setText(partner.getFirstname());
            lastnameTF.setText(partner.getLastname());
            companyTF.setText(((RolePartner)partner.getRole()).getCompany());
            emailTF.setText(partner.getEmailAddress());
            confirmEmailTF.setText(partner.getEmailAddress());
        }
    }
}
