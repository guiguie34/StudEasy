package com.github.studeasy.gui.controller.home;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

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

    public HomeAbstractController() {
        this.ROUTER = UserRouter.getInstance();
        this.FACADE = FacadeUser.getInstance();
    }

    /**
     * Disconnect from the platform and comeback to the login
     * @param event
     */
    public void disconnect(ActionEvent event) {
        try{
            Session.getInstance().disconnect();
            ROUTER.changeView(UserRouter.LOGIN_FXML_PATH,event);
        }catch (Exception e){
            e.printStackTrace();
            failLabel.setAlignment(Pos.CENTER);
            failLabel.setTextFill(Paint.valueOf("red"));
            failLabel.setText("Error when disconnecting, try again");
        }
    }

}
