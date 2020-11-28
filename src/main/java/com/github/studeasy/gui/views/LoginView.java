package com.github.studeasy.gui.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

/**
 *
 */
public class LoginView extends Parent{

    /**
     *
     * @throws IOException
     */
    public LoginView() throws IOException{
        this.getChildren().add(this.load());
    }

    /**
     *
     * @return
     * @throws IOException
     */
    private Parent load() throws IOException {
        FXMLLoader loader=new FXMLLoader();
        Parent p= loader.load(LoginView.class.getClassLoader().getResource("Views/login.fxml"));
        return p;
    }
}