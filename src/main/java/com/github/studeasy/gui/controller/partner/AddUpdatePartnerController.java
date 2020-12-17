package com.github.studeasy.gui.controller.partner;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
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
     * The field to enter the company name
     */
    @FXML
    private TextField companyTF;

    @FXML
    private Label label;
    /**
     * Instantiate the parent's attributes with
     * a router and a facade used for admin controller
     */
    public AddUpdatePartnerController(){
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
    }


    public void submit(ActionEvent event) {
        // We retrieve the user inputs
        String email = emailTF.getText();
        String password = passwordTF.getText();
        String firstname = firstnameTF.getText();
        String lastname = lastnameTF.getText();
        String company = companyTF.getText();
        label.setText("");
            // We ask the facade to check
        try {
            FACADE.submitAddPartner(email, password,firstname,lastname,company);
            label.setText("Succes ! ");
        } catch (Exception e) {
            label.setText("Ajout impossible");
        }
    }

    /**
     * TODO: implement this by create new function in UserRouter
     * @param event
     * @throws IOException
     */
    public void cancel(ActionEvent event) throws IOException {
        ((UserRouter)ROUTER).backToDashboard(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
