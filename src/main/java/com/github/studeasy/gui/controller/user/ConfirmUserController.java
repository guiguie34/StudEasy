package com.github.studeasy.gui.controller.user;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeUser;
import com.github.studeasy.logic.facades.exceptions.BadKeyException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class ConfirmUserController {

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
     * Label only visible when an error occurs
     */
    @FXML
    private Label failLabel;

    /**
     * TextField of the key
     */
    @FXML
    private TextField keyTF;

    /**
     * The user who wants to be confirmed
     */
    private final String email;

    public ConfirmUserController(String email) {
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
        this.email = email;
    }

    /**
     * ask to the DAO to confirm the account if the key is correct
     * @param event
     */
    public void confirmAccount(ActionEvent event){
        String key = keyTF.getText();
        try {
            if(FACADE.confirmAccount(email,key)){
                ROUTER.changeView(UserRouter.LOGIN_FXML_PATH,event);
            }else{
                throw new BadKeyException("The key doesn't correspond");
            }

        }catch (BadKeyException e) {
            failLabel.setAlignment(Pos.CENTER);
            failLabel.setTextFill(Paint.valueOf("red"));
            failLabel.setText(e.getMessage());

        }
        catch (Exception e){
            e.printStackTrace();
            failLabel.setAlignment(Pos.CENTER);
            failLabel.setTextFill(Paint.valueOf("red"));
            failLabel.setText("Error, try again later");
        }
    }

    /**
     * Triggered when the user pushed the cancel button
     * Cancel the action and redirect to the login page
     * @param event
     */
    public void cancel(ActionEvent event) {
        try {
            ROUTER.changeView(UserRouter.LOGIN_FXML_PATH,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
