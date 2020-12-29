package com.github.studeasy.gui.controller.coupon;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CouponRouter;
import com.github.studeasy.logic.common.Coupon;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.FacadeCoupon;
import com.github.studeasy.logic.facades.FacadeUser;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller used to add or update a coupon
 */
public class AddUpdateCouponController implements Initializable {

    /**
     * The router used by the controller
     */
    private final AbstractRouter ROUTER;

    /**
     * The facade coupon used by the controller
     */
    private final FacadeCoupon FACADE_COUPON;

    /**
     * The facade user used by the controller
     */
    private final FacadeUser FACADE_USER;

    /**
     * The coupon to update, or null
     */
    private Coupon coupon;

    /**
     * Indicates if we add or update
     * Add -> 0
     * update -> 1
     */
    private int addUpdate;

    /**
     * The button used to add or update
     */
    @FXML
    private Button addUpdateB;

    /**
     * The label displaying add or update
     */
    @FXML
    private Label addUpdateL;

    /**
     * The label displaying the errors
     */
    @FXML
    private Label addCouponFailedL;

    /**
     * The title of the coupon
     */
    @FXML
    private TextField titleCouponTF;

    /**
     * The description of the coupon
     */
    @FXML
    private TextArea descriptionCouponTF;

    /**
     * The owner of the coupon
     */
    @FXML
    private ChoiceBox<User> ownerCouponCB;

    /**
     * THe quantity of coupons
     */
    @FXML
    private Spinner<Integer> quantityCouponsS;

    /**
     * THe cost of the coupon
     */
    @FXML
    private Spinner<Integer> costCouponS;

    /**
     * Instantiate the attributes with
     * a router and a facade used for coupons
     */
    public AddUpdateCouponController(Coupon coupon, int addUpdate){
        this.ROUTER = CouponRouter.getInstance();
        this.FACADE_COUPON = FacadeCoupon.getInstance();
        this.FACADE_USER = FacadeUser.getInstance();
        this.coupon = coupon;
        this.addUpdate = addUpdate;
    }

    /**
     * Triggered when the user wants to go back
     * @param event the event triggered
     */
    public void cancel(ActionEvent event){
        try{
            ROUTER.adminRestricted(CouponRouter.COUPON_FXML_PATH,event);
        }
        catch(IOException err){
            err.printStackTrace();
        }
    }

    /**
     * Triggered when the admin wants to add a coupon
     * @param event the event triggered
     */
    public void addCoupon(ActionEvent event){
        String titleCoupon = this.titleCouponTF.getText();
        String descriptionCoupon = this.descriptionCouponTF.getText();
        User owner = this.ownerCouponCB.getValue();
        int quantityCoupons = this.quantityCouponsS.getValue();
        int costCoupon = this.costCouponS.getValue();
        // We first check if the title and the description are not empty
        if(!titleCoupon.isEmpty() && !descriptionCoupon.isEmpty()){
            // We ask the student if he is sure to add the service
            if(AbstractRouter.confirmationBox("Are you sure you want to add this coupon ?",
                    "Confirmation: "+ addUpdateL.getText(),
                    "Stud'Easy - Confirmation")){
                // We add the coupon
                try {
                    FACADE_COUPON.addCoupon(titleCoupon,descriptionCoupon,owner,quantityCoupons,costCoupon);
                    ROUTER.adminRestricted(CouponRouter.COUPON_FXML_PATH,event);
                } catch (BadInformationException err) {
                    addCouponFailedL.setTextFill(Paint.valueOf("red"));
                    addCouponFailedL.setText(err.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            addCouponFailedL.setTextFill(Paint.valueOf("red"));
            addCouponFailedL.setText("All the fields must be completed");
        }
    }

    /**
     * Triggered when the admin wants to update a coupon
     * @param event the event triggered
     */
    public void updateCoupon(ActionEvent event){

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
        // We initialize the choice box with the partners
        try {
            ownerCouponCB.setItems(FXCollections.observableArrayList(FACADE_USER.getAllPartner()));
            // The first item is selected by default

            switch(addUpdate){
                case 1:
                    // We update
                    this.addUpdateL.setText("Update a Coupon");
                    this.addUpdateB.setText("Update");
                    ownerCouponCB.setValue(coupon.getOwner());
                    this.addUpdateB.setOnAction(this::updateCoupon);
                    break;
                default:
                    // We add
                    this.addUpdateL.setText("Add a Coupon");
                    this.addUpdateB.setText("Add");
                    ownerCouponCB.getSelectionModel().selectFirst();
                    this.addUpdateB.setOnAction(this::addCoupon);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
