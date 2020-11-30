package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.views.FXMLLoaderView;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.SessionI;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Abstract class for the routers
 * It's the parent class for all the routers, they all contain
 * a session, used to safely navigate a user through the views
 * he is allowed to view
 */
public abstract class AbstractRouter {

    /**
     * The session containing the current user using the app
     * With the interface, we can only access methods to read it
     */
    protected SessionI session;

    /**
     * Instantiate the session
     */
    public AbstractRouter(){
        this.session = Session.getInstance();
    }

    /**
     * This method is used to change the view properly, it displays the new
     * fxml file
     * @param pathFXML, the path to the fxml file
     * @param event, the action trigerring the change of view
     * @throws IOException if an error occurs
     */
    public void changeView(String pathFXML, ActionEvent event) throws IOException {
        // The stage that will contain the new view
        Stage dialogStage;
        // We retrieve the node we are
        Node node = (Node)event.getSource();
        // We now retrieve the current window, to update it properly
        dialogStage = (Stage) node.getScene().getWindow();
        // We load the new fxml and make it visible then
        dialogStage.getScene().setRoot(FXMLLoaderView.load(pathFXML));
        dialogStage.show();
    }
}
