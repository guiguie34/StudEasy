package com.github.studeasy.gui.controller.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller for the student profile view
 */
public class MyProfileController implements Initializable {
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
     * Load the current user (to have all information of a student)
     */
    private final User user;

    /**
     * Label of the firstName
     */
    @FXML
    private Label firstNameTF;

    /**
     * Label of the lastName
     */
    @FXML
    private Label lastNameTF;

    /**
     * Label of the email
     */
    @FXML
    private Label emailTF;

    /**
     * Label if an error occur
     */
    @FXML
    private Label deleteFailLabel;



    /**
     * Instantiate the parent's attributes with
     * a router and a facade used for users
     */
    public MyProfileController(){
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
        this.user = Session.getInstance().getCurrentUser();


    }

    /**
     * Triggered when the user wants to go to the delete his account
     * @param event the event triggered
     */
    public void deleteMyAccount(ActionEvent event) {
        try {
            if(AbstractRouter.confirmationBox("Do you really want to delete your account ? You will miss us","Confirm the deletion","Warning")){
                FACADE.deleteAccount(user.getEmailAddress());
                ROUTER.changeView(UserRouter.LOGIN_FXML_PATH,event);
            }
        }catch (Exception e){
            e.printStackTrace();
            deleteFailLabel.setAlignment(Pos.CENTER);
            deleteFailLabel.setTextFill(Paint.valueOf("red"));
            deleteFailLabel.setText("Error, try again later");
        }

    }

    /**
     * Triggered when the user wants to go to the modify his information
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void loadUpdate(ActionEvent event) throws IOException {
        ((UserRouter)ROUTER).registerOrUpdateUser(UserRouter.REGISTER_FXML_PATH,event,1);
    }

    /**
     * Triggered when the user pushed the cancel button
     * Cancel the action and redirect to the home of the user
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        ROUTER.changeView(UserRouter.HOME_STUDENT_FXML_PATH,event);
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
        firstNameTF.setText(user.getFirstname());
        lastNameTF.setText(user.getLastname());
        emailTF.setText(user.getEmailAddress());
    }
}
