package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.job.AddUpdateJobOfferController;
import com.github.studeasy.gui.controller.job.JobOffersController;
import com.github.studeasy.gui.controller.job.SeeJobOfferController;
import com.github.studeasy.logic.common.Job;
import com.github.studeasy.logic.common.Session;
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
     * Path to see all jobs
     */
    private final static String SEE_ALL_JOB_FXML_PATH = "views/job/JobOffersController.fxml";

    /**
     * Path to add or update job
     */
    private final static String ADD_UPDATE_JOB_FXML_PATH = "views/job/addUpdateJob.fxml";

    /**
     * Path to see one job
     */
    private final static String SEE_JOB_FXML_PATH = "views/job/seeJobOfferController.fxml";

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
            AddUpdateJobOfferController addUpdateJobController = new AddUpdateJobOfferController(addUpdate, (Job)jobToUpdate);
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


    /**
     * Function loading the appropriate view to view all jobs
     *
     * @param event the action triggering this method
     * @throws IOException if an error occurs
     */
    public void viewJobs(ActionEvent event) throws IOException {

            // We load the right FXML
            FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(SEE_ALL_JOB_FXML_PATH));
            // We create the controller
            JobOffersController jobOffersController = new JobOffersController(((Session)SESSION).getCurrentUser());
            // We link this controller with the FXML
            loader.setController(jobOffersController);
            Parent root = loader.load();
            // And we change the view
            this.changeView(event, root);


    }

    /**
     * Function loading the appropriate view to see one job
     * @param event the action triggering the method
     * @throws IOException if an error occurs
     */
    public void seeJob(ActionEvent event,Object job) throws IOException {

            // We load the right FXML
            FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(SEE_JOB_FXML_PATH));
            // We create the controller
            SeeJobOfferController jobController = new SeeJobOfferController((Job)job,((Session)SESSION).getCurrentUser());
            // We link this controller with the FXML
            loader.setController(jobController);
            Parent root = loader.load();
            // And we change the view
            this.changeView(event, root);

    }


}
