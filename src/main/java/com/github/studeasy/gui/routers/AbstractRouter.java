package com.github.studeasy.gui.routers;

import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.SessionI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Abstract class for the routers
 * It's the parent class for all the routers, they all contain
 * a session, used to safely navigate a user through the views
 * he is allowed to see
 */
public abstract class AbstractRouter {

    /**
     * The session containing the current user using the app
     * With the interface, we can only access methods to read it
     */
    protected final SessionI SESSION;

    /**
     * Instantiate the session
     */
    public AbstractRouter(){
        this.SESSION = Session.getInstance();
    }

    /**
     * Path for the login file
     */
    public final static String LOGIN_FXML_PATH = "Views/login.fxml";

    /**
     * Allows to change the view (the fxml file to display)
     * @param pathFXML the path indicating where is the fxml
     * @return the Parent with the FXML loaded
     * @throws IOException if an error occurs while loading the fxml
     */
    public static Parent load(String pathFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent p= loader.load(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        return p;
    }

    /**
     * This method is used to change the view properly, it displays the new
     * fxml file
     * @param pathFXML the path to the fxml file
     * @param event the action trigerring the change of view
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
        dialogStage.getScene().setRoot(load(pathFXML));
        dialogStage.show();
    }

    /**
     * Triggered when a user tries to access a restricted view for admin
     * @param pathFXML the path to the fxml file
     * @param event the action trigerring the change of view
     * @throws IOException if an error occurs
     */
    public void adminRestricted(String pathFXML,ActionEvent event) throws IOException {
        if(SESSION.isAdmin()){
            changeView(pathFXML,event);
        }
    }

    /**
     * Triggered when a user tries to access a restricted view for student
     * @param pathFXML the path to the fxml file
     * @param event the action trigerring the change of view
     * @throws IOException if an error occurs
     */
    public void studentRestricted(String pathFXML,ActionEvent event) throws IOException {
        if (SESSION.isStudent()) {
            changeView(pathFXML, event);
        }
    }

    /**
     * Triggered when a user tries to access a restricted view for partner
     * @param pathFXML the path to the fxml file
     * @param event the action trigerring the change of view
     * @throws IOException if an error occurs
     */
    public void partnerRestricted(String pathFXML,ActionEvent event) throws IOException {
        if (SESSION.isPartner()) {
            changeView(pathFXML, event);
        }
    }
}