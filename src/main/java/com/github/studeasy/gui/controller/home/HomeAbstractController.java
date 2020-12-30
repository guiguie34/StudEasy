package com.github.studeasy.gui.controller.home;

import com.github.studeasy.gui.controller.notifications.ButtonNotificationController;
import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.facades.FacadeNotification;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;

public abstract class HomeAbstractController  {
    /**
     * label displayed if an error occur
     */
    @FXML
    protected Label failLabel;
    /**
     * The router used by the controller
     */
    protected final AbstractRouter ROUTER;


    /**
     * The facade used by the controller
     */
    protected final FacadeUser FACADE;

    /**
     * The facade for notification used by the controller
     */
    protected final FacadeNotification FACADE_NOTIF;

    @FXML
    private ButtonNotificationController clocheController;


    public HomeAbstractController() {
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
        this.FACADE_NOTIF = FacadeNotification.getInstance();
    }

    /**
     * Handle the click
     * @param event
     */
    @FXML
    public void clickNotif(MouseEvent event) throws IOException {
        clocheController.clickNotif(event);
    }

    /**
     * Disconnect from the platform and comeback to the login
     * @param event
     */
    public void disconnect(ActionEvent event) {
        try{
            Session.getInstance().disconnect();
            FACADE_NOTIF.stopTimer();
            ROUTER.changeView(UserRouter.LOGIN_FXML_PATH,event);
        }catch (Exception e){
            e.printStackTrace();
            failLabel.setAlignment(Pos.CENTER);
            failLabel.setTextFill(Paint.valueOf("red"));
            failLabel.setText("Error when disconnecting, try again");
        }
    }

}
