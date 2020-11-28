package com.github.studeasy.gui.controller;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.logic.facades.AbstractFacade;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractController implements Initializable {

    protected AbstractRouter router;
    protected AbstractFacade facade;

    public AbstractController(AbstractRouter router, AbstractFacade facade) {
        this.router = router;
        this.facade = facade;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}