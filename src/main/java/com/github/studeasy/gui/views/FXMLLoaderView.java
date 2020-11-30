package com.github.studeasy.gui.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

/**
 * This class contains the function loading the fxml files
 */
public class FXMLLoaderView{

    /**
     * Allows to change the view (the fxml file to display)
     * @param pathFXML, the path indicating where is the fxml
     * @return the Parent with the FXML loaded
     * @throws IOException if an error occurs while loading the fxml
     */
    public static Parent load(String pathFXML) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        Parent p= loader.load(FXMLLoaderView.class.getClassLoader().getResource(pathFXML));
        return p;
    }
}