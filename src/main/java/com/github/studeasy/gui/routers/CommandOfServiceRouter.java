package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.service.*;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class CommandOfServiceRouter extends AbstractRouter{
    /**
     * Singleton of the CommandOfService router
     */
    private static CommandOfServiceRouter coomandserviceRouter = null;

    /**
     * Path to the view buy or apply service
     */
    public final static String BUY_SERVICE_FXML_PATH = "views/commandService/buyService.fxml";

    /**
     * Path to the page historic command
     */

    public final static String SERVICES_BOUGHT_FXML_PATH = "views/commandService/HistoricCommand.fxml";

    /**
     * Calls the parent constructor, getting the
     * instance of the session
     */
    private CommandOfServiceRouter(){
        super();
    }

    /**
     * Used to return the unique instance of the CommandOfServiceRouter
     * @return commandofServiceRouter
     */
    public static CommandOfServiceRouter getInstance(){
        if(coomandserviceRouter == null){
         coomandserviceRouter = new CommandOfServiceRouter();
        }
        return coomandserviceRouter;
    }

    /**
     * Load the view and display the good label for proposing/requesting
     * a service
     * @param pathFXML the path to the fxml file
     * @param event the action triggering the change of view
     * @param proposeRequest 0 if we propose, 1 if we request, 2 if we update
     * @param origin to know from where come from the student, 0 -> MyServices, 1 -> seeAllServices and 2 -> Home Student
     * @throws IOException if an error occurs
     */
    public void proposeOrRequestService(String pathFXML, ActionEvent event, int proposeRequest, int origin, Service service) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with proposeRequest telling if we propose or request
        ProposeAskServiceController proposeAskServiceController = new ProposeAskServiceController(proposeRequest, origin, service);
        // We link this controller with the FXML
        loader.setController(proposeAskServiceController);
        Parent root = loader.load();
        // And we change the view
      //  this.changeView(event,root);
    }

    public void buyOrApplyService(String pathFXML, ActionEvent event, int commandRequest, int demandeuser, CommandOfService command) throws IOException{

    }


}
