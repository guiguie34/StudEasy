package com.github.studeasy.gui.controller.service;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.logic.common.*;
import com.github.studeasy.logic.common.role.RoleStudent;
import com.github.studeasy.logic.facades.FacadeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ResourceBundle;

/**
 * The controller used to see one service
 */
public class ViewServiceController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     * The facade service used by the controller
     */
    private final FacadeService FACADE_SERVICE;

    /**
     * The title of the service
     */
    @FXML
    private Text titleService;

    /**
     * The description of the service
     */
    @FXML
    private TextArea descriptionService;

    /**
     * The cost of the service
     */
    @FXML
    private Text priceService;

    /**
     * The category of the service
     */
    @FXML
    private Text categoryService;

    /**
     * The owner of the service
     */
    @FXML
    private Text ownerService;

    /**
     * The date of the service
     */
    @FXML
    private Text dateService;

    /**
     * The image representing the status of the service
     */
    @FXML
    private ImageView statusImage;

    /**
     * The type of the service
     */
    @FXML
    private Text typeService;

    /**
     * The button used to delete a service
     */
    @FXML
    private Button deleteB;

    /**
     * The button to apply or buy a service
     */
    @FXML
    private Button applyBuyServiceB;

    /**
     * The validate button
     */
    @FXML
    private Button validateB;

    /**
     * The update button
     */
    @FXML
    private Button updateB;

    /**
     * The Button to see the feedbacks
     */
    @FXML
    private Button viewFeedbacksB;

    /**
     * The service to display
     */
    private Service service;

    /**
     * Used to know from where the user comes from
     * 0 manage pending services / my services
     * 1 see all services online
     */
    private int pendingAllServices;

    /**
     * Create the controller with the router, the facade
     */
    public ViewServiceController(Service service, int pendingAllServices){
        this.ROUTER = ServiceRouter.getInstance();
        this.FACADE_SERVICE = FacadeService.getInstance();
        this.service = service;
        this.pendingAllServices = pendingAllServices;
    }

    /**
     * Triggered when the user wants to delete the service
     * @param event the event triggered
     */
    public void deleteService(ActionEvent event){
        // We ask the confirmation of the deletion
        if(AbstractRouter.confirmationBox("Are you sure you want to delete this service ?",
                "Confirmation of the deletion: "+this.service.getTitle(),
                "Stud'Easy - Confirmation")){
            // We ask the facade to delete the service
            FACADE_SERVICE.deleteService(this.service);
            // We redirect
                this.cancel(event);
        }
    }

    /**
     * Triggered when the user wants to update the service
     * @param event
     */
    public void updateService(ActionEvent event){
        try {
            ((ServiceRouter)ROUTER).proposeOrRequestService(ServiceRouter.PROPOSE_ASK_SERVICE_FXML_PATH,event,2,pendingAllServices,service);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the admin wants to validate a pending service
     * @param event the event triggered
     */
    public void validateService(ActionEvent event){
        // We ask the confirmation of the validation
        if(AbstractRouter.confirmationBox("Are you sure you want to validate this service ?",
                "Confirmation of the validation: "+this.service.getTitle(),
                "Stud'Easy - Confirmation")){
            // We ask the facade to validate the service
            FACADE_SERVICE.validateService(this.service);
            // We redirect
            this.cancel(event);
        }
    }

    /**
     * Triggered when the user wants to go back
     * @param event the event triggered
     */
    public void cancel(ActionEvent event) {
        // We use the session to know where to redirect
        Session session = Session.getInstance();
        try {
            if(session.isStudent()){
                if(pendingAllServices == 0){
                    ((ServiceRouter)ROUTER).viewAllServices(ServiceRouter.MY_SERVICES_FXML_PATH,event,pendingAllServices);
                }
                else{
                    ((ServiceRouter)ROUTER).viewAllServices(ServiceRouter.ALL_SERVICES_FXML_PATH,event,pendingAllServices);
                }
            }
            else if(session.isAdmin()){
                ((ServiceRouter)ROUTER).viewAllServices(ServiceRouter.ALL_SERVICES_FXML_PATH,event,pendingAllServices);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // We fulfill all the fields
        this.titleService.setText(this.service.getTitle());
        // We give a better format to the date
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);
        String date = shortDateFormat.format(this.service.getDateCreation());
        this.dateService.setText("Posted on: "+date);
        this.descriptionService.setText(this.service.getDescription());
        this.priceService.setText("Price: "+Integer.toString(this.service.getCost()));
        // The owner and his pseudo
        User owner = this.service.getOwner();
        RoleStudent role = (RoleStudent) owner.getRole();
        this.ownerService.setText("Owner: "+owner.getFirstname()+" "+owner.getLastname()+" ("+role.getPseudo()+")");
        // The name of the category
        CategoryTag categoryS = this.service.getCategory();
        this.categoryService.setText(categoryS.getName());
        // The image representing the status
        Image img;
        switch(this.service.getStatus()){
            case(0):
                // pending
                img = new Image("images/service/pending.png");
                this.statusImage.setImage(img);
                break;
            case(1):
                // validated
                img = new Image("images/service/validated.png");
                // We can only see the feedbacks if the service is validated
                this.viewFeedbacksB.setVisible(true);
                this.statusImage.setImage(img);
                break;
        }
        // The type of the service
        switch(this.service.getTypeService()){
            case(0):
                this.typeService.setText("Proposed :");
                this.applyBuyServiceB.setText("Buy !");
                break;
            case(1):
                this.typeService.setText("Requested : ");
                this.applyBuyServiceB.setText("Apply !");
                break;
        }
        // We retrieve the current user from the session
        Session session = Session.getInstance();
        User currentUser = session.getCurrentUser();
        // We display the buttons the user is allowed to use
        // Only the admin and the owner can delete the service
        if(currentUser.getIdUser() == owner.getIdUser() || session.isAdmin()){
            deleteB.setVisible(true);
        }
        // Only the owner can update his services
        if(currentUser.getIdUser() == owner.getIdUser()){
            updateB.setVisible(true);
        }
        // We can buy or apply only if we are a student and if we're not the owner
        if(session.isStudent() && owner.getIdUser() != currentUser.getIdUser()){
            applyBuyServiceB.setVisible(true);
        }
        // Only the admin can validate a service, if it's pending
        if(session.isAdmin() && service.getStatus() == 0){
            validateB.setVisible(true);
        }
    }
}
