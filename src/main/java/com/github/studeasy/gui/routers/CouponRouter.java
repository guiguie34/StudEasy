package com.github.studeasy.gui.routers;

import com.github.studeasy.gui.controller.coupon.AddUpdateCouponController;
import com.github.studeasy.gui.controller.coupon.SeeCouponController;
import com.github.studeasy.logic.common.Coupon;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Router for the coupon routes
 */
public class CouponRouter extends AbstractRouter{

    /**
     * Singleton of the coupon router
     */
    private static CouponRouter couponRouter = null;

    /**
     * Path to the view for adding or updating a coupon
     */
    public final static String ADD_UPDATE_COUPON_FXML_PATH = "views/coupon/add_update_coupon.fxml";
    /**
     * Path to the page to see all the coupons
     */
    public final static String COUPON_FXML_PATH = "views/coupon/coupon.fxml";
    /**
     * Path to the page displaying a coupon
     */
    public final static String SEE_COUPON_FXML_PATH = "views/coupon/see_coupon.fxml";

    /**
     * Calls the parent constructor, getting the
     * instance of the session
     */
    private CouponRouter(){
        super();
    }

    /**
     * Used to return the unique instance of the CouponRouter
     * @return the couponRouter
     */
    public static CouponRouter getInstance(){
        if(couponRouter == null){
            couponRouter = new CouponRouter();
        }
        return couponRouter;
    }

    /**
     * Load the view to add or update a coupon
     * @param pathFXML the path to the fxml file
     * @param event the action triggering the change of view
     * @param coupon the coupon to update if there is one, otherwise null
     * @param addUpdate to know if we add -> 0 or update -> 1
     * @throws IOException if an error occurs
     */
    public void addUpdateCoupon(String pathFXML, Event event, Coupon coupon, int addUpdate) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with the service
        AddUpdateCouponController addUpdateCouponController = new AddUpdateCouponController(coupon, addUpdate);
        // We link this controller with the FXML
        loader.setController(addUpdateCouponController);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event,root);
    }

    /**
     * Load the view to display a coupon
     * @param pathFXML the path to the fxml file
     * @param event the action triggering the change of view
     * @param coupon the coupon to display
     * @throws IOException if an error occurs
     */
    public void seeCoupon(String pathFXML, Event event, Coupon coupon) throws IOException {
        // We load the right FXML
        FXMLLoader loader = new FXMLLoader(AbstractRouter.class.getClassLoader().getResource(pathFXML));
        // We create the controller with the service
        SeeCouponController seeCouponController = new SeeCouponController(coupon);
        // We link this controller with the FXML
        loader.setController(seeCouponController);
        Parent root = loader.load();
        // And we change the view
        this.changeView(event,root);
    }
}