package com.github.studeasy.gui.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

/**
 *
 */
public class FXMLLoaderView{

    /**
     *
     * @param pathFXML
     * @return
     * @throws IOException
     */
    public static Parent load(String pathFXML) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        Parent p= loader.load(FXMLLoaderView.class.getClassLoader().getResource(pathFXML));
        return p;
    }
}