package com.github.studeasy.gui.controller.service;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.facades.FacadeService;
import com.github.studeasy.logic.facades.FacadeUser;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * The abstract class used to display all the services
 * Can be specialized into My services for the student, or
 * to see all the services for the admin or the student
 */
public abstract class AbstractViewServicesController {

    /**
     * The router used by the controller
     */
    protected final AbstractRouter ROUTER;

    /**
     * The facade service used by the controller
     */
    protected final FacadeService FACADE_SERVICE;

    /**
     * Table view of the services
     */
    @FXML
    protected TableView servicesTV;

    /**
     * Table column of the titles
     */
    @FXML
    protected TableColumn titleColumn;

    /**
     * Table column of the descriptions
     */
    @FXML
    protected TableColumn costColumn;

    /**
     * Table column of the date
     */
    @FXML
    protected TableColumn<Service,String> dateColumn;

    /**
     * Table column of the type of service
     */
    @FXML
    protected TableColumn<Service,String> typeServiceColumn;

    /**
     * Table column for the category
     */
    @FXML
    protected TableColumn<Service,String> categoryColumn;

    /**
     * The label showing what services are displayed
     */
    @FXML
    protected Label pendingAllServicesL;

    /**
     * To filter the categories
     */
    @FXML
    protected TextField filterCategoryTF;

    /**
     * To filter the titles
     */
    @FXML
    protected TextField filterTitleTF;

    /**
     * To select the proposed services
     */
    @FXML
    protected CheckBox proposedCB;

    /**
     * To select the requested services
     */
    @FXML
    protected CheckBox requestedCB;

    /**
     * The points of the user
     */
    @FXML
    private Text pointsUserT;

    /**
     * Your points label
     */
    @FXML
    private Text yourPointsL;

    /**
     * The list of the services displayed in the table
     */
    protected ObservableList<Service> servicesList;

    /**
     * Indicates what services we should display
     * 0 the pending services
     * 1 all the services
     */
    protected int pendingAllServices;

    /**
     * Indicates if we should display the requested, the proposed or all the services
     * 2 -> we display everything
     * 1 -> we display the requested ones
     * 0 -> we display the proposed ones
     * -1 -> we display nothing
     */
    private int proposeRequestDisplayServices = 2;

    /**
     * Create the controller with the router, the facade
     */
    public AbstractViewServicesController(int pendingAllServices){
        this.ROUTER = ServiceRouter.getInstance();
        this.FACADE_SERVICE = FacadeService.getInstance();
        this.pendingAllServices = pendingAllServices;
    }

    /**
     * Triggered when a user double clicks on a service, to see
     * the information of the service
     * @param event the event triggered
     * @param service the service the user wants to see
     */
    public void viewService(MouseEvent event, Service service) {
        try {
            ((ServiceRouter) ROUTER).viewService(ServiceRouter.VIEW_SERVICE_FXML_PATH,event,service,pendingAllServices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user types text in the search text field for the category
     * It filters the data to display only the services with the categories matching what is entered
     * Also checks the data of the other textfield (filters linked)
     * @param observable
     * @param oldValue
     * @param newValue the new value entered by the user
     * @param filteredData the data filtered
     */
    public void filterCategory(ObservableValue<? extends String> observable, String oldValue, String newValue, FilteredList<Service> filteredData) {
        // We check for each service
        filteredData.setPredicate(service -> {
            // We check if we need to display with the checkboxes
            if(displayServices(service)){
                // If the text field is empty, we display all the categories
                // If the title field is also empty
                if ((newValue == null || newValue.isEmpty())
                        && (this.filterTitleTF.getText() == null || this.filterTitleTF.getText().isEmpty())) {
                    return true;
                }
                // We compare what is entered (in lower case)
                String lowerCaseFilter = newValue.toLowerCase();
                CategoryTag category = service.getCategory();
                // If the filter matches the name of the category
                // We need to check if the other text field also matches
                if (category.getName().toLowerCase().contains(lowerCaseFilter)
                        && ((this.filterTitleTF.getText() == null || this.filterTitleTF.getText().isEmpty())
                        || service.getTitle().toLowerCase().contains(this.filterTitleTF.getText()))){
                    // The service is displayed
                    return true;
                }
                else {
                    // The category name doesn't match, not displayed
                    // Or the title doesn't match
                    return false;
                }
            }
            // Not a type of service to display
            else{
                return false;
            }
        });
    }

    /**
     * Triggered when the user types text in the search text field for the title
     * It filters the data to display only the services with the categories matching what is entered
     * Also checks the data of the other textfield (filters linked)
     * @param observable
     * @param oldValue
     * @param newValue the new value entered by the user
     * @param filteredData the data filtered
     */
    public void filterTitle(ObservableValue<? extends String> observable, String oldValue, String newValue, FilteredList<Service> filteredData) {
        // We check for each service
        filteredData.setPredicate(service -> {
            // We check if we need to display
            if(displayServices(service)){
                // If the text field is empty, we display all the services
                // If the other text field is also empty
                if ((newValue == null || newValue.isEmpty())
                        && (this.filterCategoryTF.getText() == null || this.filterCategoryTF.getText().isEmpty())) {
                    return true;
                }

                // We compare what he entered (in lower case)
                String lowerCaseFilter = newValue.toLowerCase();
                CategoryTag category = service.getCategory();
                // If the filter matches the title of the service and the category
                if (service.getTitle().toLowerCase().contains(lowerCaseFilter)
                        &&  ((this.filterCategoryTF.getText() == null || this.filterCategoryTF.getText().isEmpty())
                        || category.getName().toLowerCase().contains(this.filterCategoryTF.getText()))){
                    // The service is displayed
                    return true;
                } else {
                    // The title doesn't match, not displayed
                    // or the category doesn't match
                    return false;
                }
            }
            // Not a type of service to display
            else{
                return false;
            }
        });
    }

    /**
     * We check if we need to display this service
     * @param service the services to display
     * @return true if we can display it, false otherwise
     */
    private boolean displayServices(Service service){
        switch(this.proposeRequestDisplayServices){
            case(2):
                // We display all the services
                return true;
            default:
                // We display only the services selected
                if(service.getTypeService() == this.proposeRequestDisplayServices){
                    return true;
                }
                else{
                    return false;
                }
        }
    }

    /**
     * Update the services to display
     * @param event the event triggered
     * @param filteredData the filtered data of services
     */
    public void updateSelection(ActionEvent event, FilteredList<Service> filteredData){
        // We check what is selected or not
        if(this.proposedCB.isSelected() && this.requestedCB.isSelected()){
            // We have to display all the services
            this.proposeRequestDisplayServices = 2;
        }
        else if(this.proposedCB.isSelected()){
            // We only have the proposed services
            this.proposeRequestDisplayServices = 0;
        }
        else if(this.requestedCB.isSelected()){
            // We have to display the requested services
            this.proposeRequestDisplayServices = 1;
        }
        else{
            this.proposeRequestDisplayServices = -1;
        }
        // We update the data to display according to the text fields
        this.filterTitle(null, null, this.filterTitleTF.getText(), filteredData);
        this.filterCategory(null, null, this.filterCategoryTF.getText(), filteredData);
    }

    /**
     * Triggered when the user wants to go back
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        Session session = Session.getInstance();
        if(session.isAdmin()){
            ROUTER.adminRestricted(UserRouter.HOME_ADMIN_FXML_PATH,event);
        }
        if(session.isStudent()){
            ROUTER.studentRestricted(UserRouter.HOME_STUDENT_FXML_PATH,event);
        }
    }

    /**
     * Function initializing the common columns of the table view
     */
    protected void commonInitialize(){
        // In case we don't have any services
        servicesTV.setPlaceholder(new Label("There is no service to display"));
        // According to the role of the user, we won't display the same things
        // We retrieve all the services and put them in an observable list
        Session session = Session.getInstance();
        // Admin managing the pending services
        if (session.isAdmin() && pendingAllServices == 0) {
            this.pendingAllServicesL.setText("Pending Services");
            // We only retrieve the pending services
            servicesList = FXCollections.observableArrayList(FACADE_SERVICE.getPendingServices());
        }
        else if(session.isStudent() && pendingAllServices == 0){
            this.pendingAllServicesL.setText("My Services");
            // We only retrieve the services of the user
            servicesList = FXCollections.observableArrayList(FACADE_SERVICE.getMyServices());
        }
        // We display all the services
        else {
            this.pendingAllServicesL.setText("All Services Online");
            // We retrieve all the services
            servicesList = FXCollections.observableArrayList(FACADE_SERVICE.getOnlineServices());
        }
        if(session.isStudent()){
            FacadeUser facadeUser = FacadeUser.getInstance();
            // To see how many points he has
            int pointsUser = facadeUser.viewPoints();
            this.pointsUserT.setText(Integer.toString(pointsUser));
            this.yourPointsL.setVisible(true);
        }
        // We put the titles of the services on the right column
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<Service,String>("title")
        );
        // Same for the cost
        costColumn.setCellValueFactory(
                new PropertyValueFactory<Service,Integer>("cost")
        );
        // We give a better format to the date
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);
        // The date of creation
        dateColumn.setCellValueFactory(c -> {
            Service serv = c.getValue();
            Date dateCreation = serv.getDateCreation();
            return new SimpleObjectProperty<String>(shortDateFormat.format(dateCreation));
        });
        // The type of service
        typeServiceColumn.setCellValueFactory(c -> {
            // We retrieve the service
            Service serv = c.getValue();
            switch(serv.getTypeService()){
                case 0:
                    // Service proposed
                    return new SimpleObjectProperty<>("Proposed");
                case 1:
                    // Service requested
                    return new SimpleObjectProperty<>("Requested");
                default:
                    return new SimpleObjectProperty<>("Unknown");
            }
        });
        // The category of the service
        categoryColumn.setCellValueFactory(c -> {
            Service serv = c.getValue();
            CategoryTag cat = serv.getCategory();
            return new SimpleObjectProperty<>(cat.getName());
        });

        // We add the data in the table
        servicesTV.setItems(servicesList);

        // Put a listener (double click) on each row to go to the information of a service
        servicesTV.setRowFactory( tv -> {
            TableRow<Service> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Service rowData = row.getItem();
                    this.viewService(event,rowData);
                }
            });
            return row ;
        });

        // We create a filtered list containing the data
        FilteredList<Service> filteredServices = new FilteredList<>(servicesList, p -> true);
        // We associate the text fields with a function listening to what is entered (to filter data)
        filterCategoryTF.textProperty().addListener((observable,oldValue,newValue) -> this.filterCategory(observable,oldValue,newValue,filteredServices));
        filterTitleTF.textProperty().addListener((observable,oldValue,newValue) -> this.filterTitle(observable,oldValue,newValue,filteredServices));
        // We do the same with the checkboxes to filter the services according to their type
        proposedCB.setOnAction((event) -> this.updateSelection(event,filteredServices));
        requestedCB.setOnAction((event) -> this.updateSelection(event,filteredServices));
        // We create a sorted list based on our filtered data
        SortedList<Service> sortedData = new SortedList<>(filteredServices);
        sortedData.comparatorProperty().bind(servicesTV.comparatorProperty());
        // We add the sorted data in the table
        servicesTV.setItems(sortedData);
    }
}