package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.views.FXMLLoaderView;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.SessionI;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractRouter {

    protected SessionI session;

    public AbstractRouter(){
        this.session = Session.getInstance();
    }

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
