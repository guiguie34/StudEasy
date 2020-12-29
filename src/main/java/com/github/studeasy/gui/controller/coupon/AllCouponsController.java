package com.github.studeasy.gui.controller.coupon;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CouponRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.facades.FacadeCoupon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller used to see all the coupons
 */
public class AllCouponsController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     /**
     * The facade used by the controller
     */
    private final FacadeCoupon FACADE;

    /**
     * The table view displaying the coupons
     */
    @FXML
    private TableView couponsTV;

    /**
     * The text field used to filter the coupons
     */
    @FXML
    private TextField filterCouponTF;

    /**
     * The button used to add a coupon
     */
    @FXML
    private Button addCouponB;

    /**
     * Instantiate the attributes with
     * a router and a facade used for coupons
     */
    public AllCouponsController(){
        this.ROUTER = CouponRouter.getInstance();
        this.FACADE = FacadeCoupon.getInstance();
    }

    /**
     * Triggered when the admin wants to add a coupon
     * @param event the event triggered
     */
    public void addCoupon(ActionEvent event){
        try {
            ((CouponRouter)ROUTER).addUpdateCoupon(CouponRouter.ADD_UPDATE_COUPON_FXML_PATH,event,null,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user wants to go back
     * @param event the event triggered
     */
    public void cancel(ActionEvent event){
        // We redirect the user according to his role
        Session session = Session.getInstance();
        try{
            if(session.isAdmin()){
                ROUTER.adminRestricted(UserRouter.HOME_ADMIN_FXML_PATH,event);
            }
            else if(session.isStudent()){
                ROUTER.studentRestricted(UserRouter.HOME_STUDENT_FXML_PATH,event);
            }
        }
        catch(IOException err){
            err.printStackTrace();
        }
    }

    /**
     * Function from the interface Initializable
     * Make changes to the controller and its view before
     * the view appears on the client side
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Session session = Session.getInstance();
        if(session.isAdmin()){
            // The admin can add coupons
            addCouponB.setVisible(true);
        }
    }
}
