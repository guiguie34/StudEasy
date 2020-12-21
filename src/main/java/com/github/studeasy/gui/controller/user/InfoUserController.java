package com.github.studeasy.gui.controller.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.common.role.RoleStudent;
import com.github.studeasy.logic.facades.FacadeUser;
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
 * The controller for the user management view, edit a user / delete a user
 */
public class InfoUserController implements Initializable {
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
     * The label for the email of a user
     */
    @FXML
    private Label emailTF;

    /**
     * The label for the pseudo of a user
     */
    @FXML
    private Label pseudoTF;

    /**
     * The label for the name of a user
     */
    @FXML
    private Label titleTF;

    /**
     * The label if an error occur
     */
    @FXML
    private Label failLabel;

    /**
     * The label for the points of an user
     */
    @FXML
    private Label pointsTF;

    /**
     * The user we want to display
     */
    private final User user;
    public InfoUserController(User user) {
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
        this.user = user;
    }

    /**
     * Triggered when the user wants to go to the delete his account
     * @param event the event triggered
     */
    public void deleteAccount(ActionEvent event) {
        try {
            if(AbstractRouter.confirmationBox("Do you really want to delete this user ? ","Confirm the deletion","Warning")){
                FACADE.deleteAccount(user.getEmailAddress());
                ROUTER.changeView(UserRouter.SEARCH_USER_FXML_PATH,event);
            }
        }catch (Exception e){
            e.printStackTrace();
            failLabel.setAlignment(Pos.CENTER);
            failLabel.setTextFill(Paint.valueOf("red"));
            failLabel.setText("Error when deleting, try again later");
        }
    }

    /**
     * Triggered when the user wants to display all the services created by an user
     * @param event
     */
    public void seeHisServices(ActionEvent event){

    }


    /**
     * Triggered when the user pushed the cancel button
     * Cancel the action and redirect to the login page
     * @param event
     */
    public void cancel(ActionEvent event) {
        try {
            ROUTER.changeView(UserRouter.SEARCH_USER_FXML_PATH,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        emailTF.setText(user.getEmailAddress());
        pseudoTF.setText(((RoleStudent)user.getRole()).getPseudo());
        titleTF.setText(user.getFirstname() + " " + user.getLastname());
        pointsTF.setText(String.valueOf(((RoleStudent)user.getRole()).getPoints()));

    }
}
