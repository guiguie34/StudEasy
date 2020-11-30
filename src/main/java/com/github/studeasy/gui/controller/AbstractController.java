package com.github.studeasy.gui.controller;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.logic.facades.AbstractFacade;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Abstract class for the Controllers
 * It's the parent class for all the controllers, they all contain
 * a router, used to navigate through the views and a facade to use
 * the methods from the business logic
 */
public abstract class AbstractController implements Initializable {

    /**
     * The router used by the controller
     */
    protected AbstractRouter router;
    /**
     * The facade used by the controller
     */
    protected AbstractFacade facade;

    /**
     * Instantiate the router and facade according to what's given
     * in parameters
     * @param router, the router used
     * @param facade, the facade used
     */
    public AbstractController(AbstractRouter router, AbstractFacade facade) {
        this.router = router;
        this.facade = facade;
    }

    /**
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}