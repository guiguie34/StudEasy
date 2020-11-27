package com.github.studeasy.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {


    public void onButtonPress(ActionEvent av){
        System.out.println(av);
        System.out.println(textF.getText());
    }
    @FXML
    private TextField textF;
}
