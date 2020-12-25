package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.job.AddUpdateJobOfferController;
import com.github.studeasy.gui.controller.partner.AddUpdatePartnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Router for the job's routes
 */
public class JobRouter extends AbstractRouter {
    /**
     * Singleton of the jobRouter router
     */
    private static JobRouter jobRouter = null;

    /**
     * Calls the parent constructor, getting the
     * instance of the session
     */
    private JobRouter() {
        super();
    }

    /**
     * Used to return the unique instance of the JobRouter
     *
     * @return
     */
    public static JobRouter getInstance() {
        if (jobRouter == null) {
            jobRouter = new JobRouter();
        }
        return jobRouter;
    }

    /**
     * Path to the handle Job
     */
    private final static String HANDLE_JOB_FXML_PATH = "views/job/handleJob.fxml";

    /**
     * Path to add or update Job
     */
    private final static String ADD_UPDATE_JOB_FXML_PATH = "views/job/addUpdateJob.fxml";

    /**
     * Path to the home student view
     */
    private final static String HOME_STUDENT_FXML_PATH = "views/user/homeStudent.fxml";
    /**
     * Path to the home admin view
     */
    private final static String HOME_ADMIN_FXML_PATH = "views/user/homeAdmin.fxml";
    /**
     * Path to the home partner view
     */
    private final static String HOME_PARTNER_FXML_PATH = "views/user/homePartner.fxml";



    /**
     * Load the view for the partner to handle his job offer
     *
     * @param event
     * @throws IOException
     */
    public void handleJob(ActionEvent event) throws IOException {
        partnerRestricted(HANDLE_JOB_FXML_PATH, event);
    }

    /**
     * Function loading the appropriate view to add job for a partner
     *
     * @param event the action triggering this method
     * @throws IOException if an error occurs
     */
    public void addOrUpdateJob(ActionEvent event, int addUpdate, Object jobToUpdate) throws IOException {
        if (SESSION.isPartner()) {
            // We load the right FXML
            FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(ADD_UPDATE_JOB_FXML_PATH));
            // We create the controller with addUpdate telling if we add or update
            AddUpdateJobOfferController addUpdateJobController = new AddUpdateJobOfferController(addUpdate, jobToUpdate);
            // We link this controller with the FXML
            loader.setController(addUpdateJobController);
            Parent root = loader.load();
            // And we change the view
            this.changeView(event, root);
        }

    }

    /**
     * Function loading dashboard for each kind of user
     *
     * @param event the action triggering this method
     * @throws IOException if an error occurs
     */
    public void backToDashboard(ActionEvent event) throws IOException {
        studentRestricted(HOME_STUDENT_FXML_PATH, event);
        adminRestricted(HOME_ADMIN_FXML_PATH, event);
        partnerRestricted(HOME_PARTNER_FXML_PATH, event);
    }
}
