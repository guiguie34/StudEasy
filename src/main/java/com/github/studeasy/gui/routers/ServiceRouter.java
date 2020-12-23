package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.service.AllServicesController;
import com.github.studeasy.gui.controller.service.ProposeAskServiceController;
import com.github.studeasy.gui.controller.service.ViewServiceController;
import com.github.studeasy.logic.common.Service;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Router for the services routes
 */
public class ServiceRouter extends AbstractRouter{

    /**
     * Singleton of the service router
     */
    private static ServiceRouter serviceRouter = null;

    /**
     * Path to the view displaying a service
     */
    public final static String VIEW_SERVICE_FXML_PATH = "views/service/view_service.fxml";
    /**
     * Path to the page to create a service
     */
    public final static String PROPOSE_ASK_SERVICE_FXML_PATH = "views/service/propose_ask_service.fxml";
    /**
     * Path to the page displaying the services of a user
     */
    public final static String MY_SERVICES_FXML_PATH = "views/service/my_services.fxml";
    /**
     * Path to the page displaying all the services
     */
    public final static String ALL_SERVICES_FXML_PATH = "views/service/all_services.fxml";
    /**
     * Path to the page displaying the services bought
     */
    public final static String SERVICES_BOUGHT_FXML_PATH = "views/service/services_bought.fxml";

     /**
     * Calls the parent constructor, getting the
     * instance of the session
     */
    private ServiceRouter(){
        super();
    }

    /**
     * Used to return the unique instance of the ServiceRouter
     * @return the serviceRouter
     */
    public static ServiceRouter getInstance(){
        if(serviceRouter == null){
            serviceRouter = new ServiceRouter();
        }
        return serviceRouter;
    }

    /**
     * Load the view and display the good label for proposing/requesting
     * a service
     * @param pathFXML the path to the fxml file
     * @param event the action triggering the change of view
     * @param proposeRequest 0 if we propose, 1 if we request
     * @throws IOException if an error occurs
     */
    public void proposeOrRequestService(String pathFXML, ActionEvent event, int proposeRequest) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with proposeRequest telling if we propose or request
        ProposeAskServiceController proposeAskServiceController = new ProposeAskServiceController(proposeRequest);
        // We link this controller with the FXML
        loader.setController(proposeAskServiceController);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event,root);
    }

    /**
     * Load the view and display the service
     * @param pathFXML the path to the fxml file
     * @param event the action triggering the change of view
     * @param service the service to display
     * @param pendingAllServices to know where the user come from (0 -> manage pending/my services, 1 -> see all Services)
     * @throws IOException if an error occurs
     */
    public void viewService(String pathFXML, Event event, Service service, int pendingAllServices) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with the service
        ViewServiceController viewServiceController = new ViewServiceController(service, pendingAllServices);
        // We link this controller with the FXML
        loader.setController(viewServiceController);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event,root);
    }

    /**
     * Load the view and display the service
     * @param pathFXML the path to the fxml file
     * @param event the action triggering the change of view
     * @param pendingAllServices 0 if the admin manages the services, 1 to see everything
     * @throws IOException if an error occurs
     */
    public void viewAllServices(String pathFXML, Event event, int pendingAllServices) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller
        AllServicesController allServicesController = new AllServicesController(pendingAllServices);
        // We link this controller with the FXML
        loader.setController(allServicesController);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event,root);
    }
}
