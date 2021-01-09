package com.github.studeasy.gui.controller.commandofService;

import com.github.studeasy.gui.controller.service.ViewServiceController;
import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CommandOfServiceRouter;
import com.github.studeasy.gui.routers.ServiceRouter;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeCommandOfService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AcceptOrDeclineController implements Initializable {

    private final AbstractRouter ROUTER ;
    /**
     * The facade CommandOfService used by the controller
     */
    private final FacadeCommandOfService FACADE_COMMANDOFSERVICE;

    /***
     * List of all the command for a user
     */
    protected ObservableList<CommandOfService> allcommandtoServiceList;

    /***
     * Table view of the commands
     */
    @FXML
    protected TableView commandtoServiceList;

    /***
     * Table column of the title
     */
    @FXML
    protected TableColumn<CommandOfService,String> titleColumnCommand;

    /***
     * Table column of the demander
     */
    @FXML
    protected TableColumn<CommandOfService,String> demanderColumn;

    /**
     * To display the error to the user
     */
    @FXML
    private Label acceptdeclineLabel;
    /***
     * Table column of the date of command
     */
    @FXML
    protected TableColumn<CommandOfService,String> dateDemandeColumn;

    /**
     * Table column of the status
     */
    @FXML
    private TableColumn<CommandOfService,String> statusDemandeColumn;

    /**
     * The column containing the buttons to accept a command
     */
    @FXML
    private TableColumn acceptColumn;

    /**
     * The column containing the buttons to decline a command
     */
    @FXML
    private TableColumn declineColumn;

    /**
     * The service to display
     */
    private Service service;

    /**
     * The command to valide
     */
    private CommandOfService command;

    /***
     * pending all services
     */
    private int pendingAllServices;



    public AcceptOrDeclineController(int pendingAllServices){
        this.FACADE_COMMANDOFSERVICE=FacadeCommandOfService.getInstance();
        this.pendingAllServices=pendingAllServices;
        this.ROUTER=ServiceRouter.getInstance();
    }



    /**
     * Function used at the initialization, to create and associate the update/delete buttons to the data
     * @param currentController the controller associated, to trigger the events with the buttons
     * @param tabC the column where we add the buttons (update or delete column)
     */
    private void addButtonsToTable(AcceptOrDeclineController currentController, TableColumn<CommandOfService,Void> tabC) {

        // cellFactory will contain our buttons
        Callback<TableColumn<CommandOfService, Void>, TableCell<CommandOfService, Void>> cellFactory = new Callback<>() {
            // For each row of the table view, we want to create the buttons
            @Override
            public TableCell<CommandOfService, Void> call(final TableColumn<CommandOfService, Void> param) {
                final TableCell<CommandOfService, Void> cell = new TableCell<>() {
                    // The image replacing the button
                    Image img;
                    // And the displayer of the image
                    final ImageView iv;
                    // We create the button
                    private final Button btn = new Button();

                    {
                        // We want it transparent, so we only see the image
                        btn.setStyle("-fx-background-color: transparent;");
                        // We assign the right button to the right column
                        switch (tabC.getId()) {
                            // accept column
                            case ("acceptColumn"):
                                img = new Image("images/common/update.png");
                                btn.setOnAction(event -> {
                                    try {
                                        currentController.acceptCommande(event, getTableView().getItems().get(getIndex()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                break;
                            // Decline column
                            case ("declineColumn"):
                                img = new Image("images/common/trash.png");
                                btn.setOnAction(event -> {
                                    try {
                                        currentController.declineCommande(event, getTableView().getItems().get(getIndex()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });                }
                        iv = new ImageView(img);
                        btn.setGraphic(iv);
                    }

                    // We only want to print the buttons on the rows containing data
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else{
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        // We add the buttons to the column
        tabC.setCellFactory(cellFactory);
    }

    /***
     * Triggered when user accept a command
     * @param event
     * @throws Exception
     */
    public void acceptCommande(ActionEvent event,CommandOfService command) throws Exception {
        // Display a message to confirm the selected command
        if(AbstractRouter.confirmationBox("Are you sure you want to accept this command ?",
                "Confirmation: ",
                "Stud'Easy - Confirmation")){
            // We ask the facade to accept the command
            FACADE_COMMANDOFSERVICE.acceptTransaction(command);
            // We remove this command from the table
            allcommandtoServiceList.removeAll(command);
            acceptdeclineLabel.setTextFill(Paint.valueOf("green"));
            acceptdeclineLabel.setText("Command accepted with success");
        }
    }

    /***
     * Triggered when user decline a command
     * @param event
     * @throws Exception
     */
    public void declineCommande(ActionEvent event,CommandOfService command) throws Exception {
        // Display a message to confirm the deletion of the selected command
        if(AbstractRouter.confirmationBox("Are you sure you want to delete this command ?",
                "Confirmation of the deletion: ",
                "Stud'Easy - Confirmation")){
            // We ask the facade to delete the command
            FACADE_COMMANDOFSERVICE.declineTransaction(command);
            // We remove this command from the table
            allcommandtoServiceList.removeAll(command);
            acceptdeclineLabel.setTextFill(Paint.valueOf("green"));
            acceptdeclineLabel.setText("Command deleted with success");
        }


    }

    /**
     * Triggered when the user wants to go back
     * @param event the event triggered
     */
    public void cancel(ActionEvent event) {

        try {

            if(pendingAllServices == 0){
                ((ServiceRouter)ROUTER).viewAllServices(ServiceRouter.MY_SERVICES_FXML_PATH,event,pendingAllServices);
            }
            else{
                ((ServiceRouter)ROUTER).viewAllServices(ServiceRouter.ALL_SERVICES_FXML_PATH,event,pendingAllServices);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Initialize view function to the view
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Pending
        final String pending = "Pending";
        // Validated
        final String validated = "Validated";
        // We get the current user
        Session session = Session.getInstance();
        try {
            allcommandtoServiceList = FXCollections.observableArrayList(FACADE_COMMANDOFSERVICE.getMyCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // set column title
        titleColumnCommand.setCellValueFactory( c -> {
                    CommandOfService command = c.getValue();
                    Service serv = command.getService();
                    return new SimpleObjectProperty<>(serv.getTitle());
                }
        );
        // set column owner
        demanderColumn.setCellValueFactory(
                c -> {
                    CommandOfService command = c.getValue();
                    Service serv = command.getService();
                    User owner = serv.getOwner();
                    return new SimpleObjectProperty<>(owner.getFirstname());
                }
        );

        // set the date
        DateFormat shortDateFormatCommande = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);
        dateDemandeColumn.setCellValueFactory(
                c -> {
                    CommandOfService command = c.getValue();
                    Date dateCreation = command.getCreationDate();
                    return new SimpleObjectProperty<String>(shortDateFormatCommande.format(dateCreation));
                });
        // get status of the command
        statusDemandeColumn.setCellValueFactory(
                c ->{
                    CommandOfService command = c.getValue();
                    if(command.getStatus()==0){
                        // Pending
                        return new SimpleObjectProperty<>(pending);
                    }
                    else{
                        return new SimpleObjectProperty<>(validated);
                    }
                }
        );
        // Add accept button
        addButtonsToTable(this,acceptColumn);
        // Add decline button
        addButtonsToTable(this,declineColumn);
        // Add data to the table
        commandtoServiceList.setItems(allcommandtoServiceList);
    }
}
