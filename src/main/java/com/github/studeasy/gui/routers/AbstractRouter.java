package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.views.FXMLLoaderView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractRouter {

    /**
     *
     * @param pathFXML
     * @param event
     * @throws IOException
     */
    public void changeView(String pathFXML, ActionEvent event) throws IOException {
        Stage dialogStage;
        Node node = (Node)event.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.getScene().setRoot(FXMLLoaderView.load(pathFXML));
        dialogStage.show();
    }
}
