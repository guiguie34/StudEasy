package com.github.studeasy.gui.controller.notifications;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.NotificationRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Notification;
import com.github.studeasy.logic.facades.FacadeNotification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller to handle the notification
 */
public class NotificationManagementController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     * The facade used by the controller
     */
    private final FacadeNotification FACADE;

    /**
     * Router for user
     */
    private final AbstractRouter ROUTER_USER;

    /**
     * Contains the list of job
     */
    @FXML
    private ListView<Notification> listview;

    /**
     * All the job
     */
    private ObservableList<Notification> notificationList= FXCollections.observableArrayList();

    @FXML
    private Label labelError;

    /**
     * Instantiates the variables
     */
    public NotificationManagementController() {
        this.ROUTER = NotificationRouter.getInstance();
        this.FACADE = FacadeNotification.getInstance();
        this.ROUTER_USER = UserRouter.getInstance();
    }

    /**
     * Load the view to return to the dashboard
     * @param event the event triggering the method
     * @throws IOException if an error occurs
     */
    public void cancel(ActionEvent event) throws IOException {
        ((UserRouter)ROUTER_USER).backToDashboard(event);
    }

    /**
     * Allows to make custom cells for job
     */
    private class NotificationListCell extends ListCell<Notification> {

        private final Label titleLabel = new Label();
        private final Button buttonSee = new Button("Mark as read");
        private final Button buttonDelete = new Button("Delete");

        private final AnchorPane content = new AnchorPane();
        private final GridPane gridPane = new GridPane();
        private final ImageView status = new ImageView("images/service/refused.png");


        public NotificationListCell() {

            /*
            Handling button click on each cells
             */
            buttonSee.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        ((FacadeNotification) FACADE).markAsRead(getItem().getId());
                        getItem().setRead(true);
                        updateItem(getItem(),false);

                    } catch (Exception e) {
                        labelError.setTextFill(Color.RED);
                        labelError.setText("Unable to mark as read the notification");
                    }
                }
            });

            buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        ((FacadeNotification) FACADE).deleteNotification(getItem().getId());
                        getListView().getItems().remove(getItem());
                    } catch (Exception e) {
                        labelError.setTextFill(Color.RED);
                        labelError.setText("Unable to delete the notification");
                    }
                }
            });

            titleLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        ((NotificationRouter)ROUTER).seeNotification(event,getItem());
                    } catch (IOException e) {
                        labelError.setTextFill(Color.RED);
                        labelError.setText("Can't see the notification");
                    }
                }
            });

            /*
             Set style for title and description
             */
            titleLabel.setStyle(" -fx-font-weight: bold; -fx-font-size: 1.5em; -fx-font-family: Arial");

            status.setFitWidth(20);
            status.setFitHeight(20);

            buttonSee.setId("neutralButton");
            buttonDelete.setId("negativeButton");
            GridPane.setConstraints(titleLabel, 0, 0);
            GridPane.setConstraints(buttonSee, 2, 0);
            GridPane.setConstraints(buttonDelete, 3, 0);
            GridPane.setConstraints(status, 1, 0);

            gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, VPos.CENTER, true));
            gridPane.setHgap(4);
            gridPane.setVgap(4);
            AnchorPane.setTopAnchor(gridPane, 0d);
            AnchorPane.setLeftAnchor(gridPane, 0d);
            AnchorPane.setBottomAnchor(gridPane, 0d);
            AnchorPane.setRightAnchor(gridPane, 0d);
            gridPane.getChildren().setAll(titleLabel, buttonSee, buttonDelete, status);
            content.getChildren().add(gridPane);
        }

        @Override
        protected void updateItem(Notification item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (!empty && item != null) {
                titleLabel.setText(item.getTitle());
                status.setVisible(false);
                buttonSee.setVisible(false);
                buttonDelete.setVisible(true);
                if(!item.isRead()) {
                    status.setVisible(true);
                    buttonSee.setVisible(true);
                }

                //Display
                setGraphic(content);
            }
            else{
                titleLabel.setText("");
                buttonSee.setVisible(false);
                buttonDelete.setVisible(false);
                setVisible(false);
                status.setVisible(false);
                setGraphic(content);

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listview.setPlaceholder(new Label("You don't have notifications, please come back later !"));

        try {
            notificationList.addAll(FACADE.getNotification());
            listview.getItems().setAll(notificationList);
            listview.setCellFactory(lv -> new NotificationListCell());
        } catch (Exception e) {
            listview.setPlaceholder(new Label("An error occurs, please retry later"));
        }

    }

}
