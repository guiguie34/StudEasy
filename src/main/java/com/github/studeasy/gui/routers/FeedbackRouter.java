package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.feedback.SeeFeedbacksServiceController;
import com.github.studeasy.gui.controller.user.InfoUserController;
import com.github.studeasy.logic.common.User;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Router for the paths linked to the feedbacks of a service
 */
public class FeedbackRouter extends AbstractRouter{

    /**
     * Singleton of the feedback router
     */
    private static FeedbackRouter feedbackRouter = null;

    /**
     * Path to the feedbacks of a service
     */
    public final static String FEEDBACKS_SERVICE_FXML_PATH = "views/feedback/seeFeedbacksService.fxml";


    /**
     * Calls the parent constructor, getting the
     * instance of the session
     */
    private FeedbackRouter(){
        super();
    }

    /**
     * Used to return the unique instance of the FeedbackRouter
     * @return an instance of the router
     */
    public static FeedbackRouter getInstance(){
        if(feedbackRouter == null){
            feedbackRouter = new FeedbackRouter();
        }
        return feedbackRouter;
    }

    /**
     *  Load the page of the feedbacks of a service
     * @param pathFXML the path to the fxml file
     * @param event the action triggering the change of view
     * @param idService the id of the service concerned
     * @throws IOException if an error occur
     */
    public void viewFeedbacks(String pathFXML, Event event, int idService) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with
        SeeFeedbacksServiceController seeFeedbacksServiceController = new SeeFeedbacksServiceController(idService);
        loader.setController(seeFeedbacksServiceController);
        Parent root = loader.load();
        this.changeView(event,root);
    }

}
