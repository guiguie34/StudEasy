package com.github.studeasy.gui.controller.commandofService;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CommandOfServiceRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeCommandOfService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class HistoricController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter COMMAND_ROUTER;

    /**
     * The facade CommandOfService used by the controller
     */
    private final FacadeCommandOfService FACADE_COMMANDOFSERVICE;

    /***
     * List of all the command for a user
     */
    protected ObservableList<CommandOfService> allcommandList;

    /***
     * Table view of the list of commands
     */
    @FXML
    protected TableView commandList;

    /***
     * Table column of the title
     */
    @FXML
    protected TableColumn<CommandOfService,String> titleColumn;

    /***
     * Table column of the owner
     */
    @FXML
    protected TableColumn<CommandOfService,String> ownerColumn;

    /***
     * Table column of the cost
     */
    @FXML
    protected TableColumn<CommandOfService,Integer> costColumn;

    /***
     * Table column of the date of command
     */
    @FXML
    protected TableColumn<CommandOfService,String> datecommandColumn;

    /**
     * Table column of the status
     */
    @FXML
    private TableColumn<CommandOfService,String> statusColumn;

    /**
     * Create the controller with the router, the facade
     */
    public HistoricController(){
        this.COMMAND_ROUTER = CommandOfServiceRouter.getInstance();
        this.FACADE_COMMANDOFSERVICE = FacadeCommandOfService.getInstance();
    }

    /**
     * Triggered when the user pushed the cancel button
     * Cancel the action and redirect to the home of the user
     * @param event the event triggered
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        COMMAND_ROUTER.changeView(UserRouter.HOME_STUDENT_FXML_PATH,event);
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
        // In case we don't have any commands
        commandList.setPlaceholder(new Label("There is currently no commands to display"));
        // Pending
        final String pending = "Pending";
        // Validated
        final String validated = "Validated";
        // We get the current user
        Session session = Session.getInstance();
        try {
            allcommandList = FXCollections.observableArrayList(FACADE_COMMANDOFSERVICE.getMyCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // set column title
        titleColumn.setCellValueFactory( c -> {
            CommandOfService command = c.getValue();
            Service serv = command.getService();
            return new SimpleObjectProperty<>(serv.getTitle());
        }
        );
        // set column owner
        ownerColumn.setCellValueFactory(
                c -> {
                    CommandOfService command = c.getValue();
                    Service serv = command.getService();
                    User owner = serv.getOwner();
                    return new SimpleObjectProperty<>(owner.getFirstname());
                }
        );
        // set cost column
        costColumn.setCellValueFactory( c -> {
                CommandOfService command = c.getValue();
                Service serv = command.getService();
                return new SimpleObjectProperty<>(serv.getCost());
        }
        );
        // set the date
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);
        datecommandColumn.setCellValueFactory(
                c -> {
                    CommandOfService command = c.getValue();
                    Date dateCreation = command.getCreationDate();
                    return new SimpleObjectProperty<String>(shortDateFormat.format(dateCreation));
                });
        // get status of the command
        statusColumn.setCellValueFactory(
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

        // Add data to the table
        commandList.setItems(allcommandList);
    }
}
