package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.commandofService.BuyOrApplyServiceController;
import com.github.studeasy.gui.controller.commandofService.HistoricController;
import com.github.studeasy.gui.controller.service.*;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.w3c.dom.html.HTMLDOMImplementation;

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

    /***
     * Load the view and display the good label for buy or apply service
     * @param pathFXML
     * @param event
     * @param command
     * @throws IOException
     */
    public void buyOrApplyService(String pathFXML, ActionEvent event, CommandOfService command,Service service) throws IOException{
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with proposeRequest telling if we buy or apply a service
        BuyOrApplyServiceController buyorapplycontroller = new BuyOrApplyServiceController(command,service);
        // We link this controller with the FXML
        loader.setController(buyorapplycontroller);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event,root);
    }

    public void historic(String pathFXML, Event event) throws IOException{
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // Creat controller for the historic
        HistoricController historicController = new HistoricController();
        // We link this controller with the FXML
        loader.setController(historicController);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event,root);
    }



}
