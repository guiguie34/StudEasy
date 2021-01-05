package com.github.studeasy.gui.controller.coupon;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CouponRouter;
import com.github.studeasy.logic.common.Coupon;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.common.role.RolePartner;
import com.github.studeasy.logic.common.role.RoleStudent;
import com.github.studeasy.logic.facades.FacadeCoupon;
import com.github.studeasy.logic.facades.exceptions.ErrorBuyCoupon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller used to see one coupon
 */
public class SeeCouponController implements Initializable {

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
     * The coupon to display
     */
    private Coupon coupon;

    /**
     * The title of the coupon
     */
    @FXML
    private Text titleT;

    /**
     * The company of the owner of the coupon
     */
    @FXML
    private Text ownerT;

    /**
     * The description of the coupon
     */
    @FXML
    private TextArea descriptionCouponTA;

    /**
     * The cost of the coupon
     */
    @FXML
    private Text costT;

    /**
     * The quantity of the coupon
     */
    @FXML
    private Text quantityT;

    /**
     * The image view of the availability
     */
    @FXML
    private ImageView availabilityImage;

    /**
     * The button used to buy a coupon
     */
    @FXML
    private Button buyB;

    /**
     * The button used to update a coupon
     */
    @FXML
    private Button updateB;

    /**
     * The button used to delete a coupon
     */
    @FXML
    private Button deleteB;

    /**
     * To display the error to the user
     */
    @FXML
    private Label errL;

    /**
     * The points of the user
     */
    @FXML
    private Text pointsUserT;

    /**
     * Your points label
     */
    @FXML
    private Text yourPointsL;

    /**
     * The number of points of the user
     */
    private int pointsUser;

    /**
     * Instantiate the attributes with
     * a router and a facade used for coupons
     */
    public SeeCouponController(Coupon coupon){
        this.ROUTER = CouponRouter.getInstance();
        this.FACADE = FacadeCoupon.getInstance();
        this.coupon = coupon;
    }

    /**
     * Triggered when the user wants to buy the coupon
     * @param event the event triggered
     */
    public void buyCoupon(ActionEvent event){
        // We check if he has enough points to buy this coupon
        if(pointsUser >= coupon.getValue()){
            if(AbstractRouter.confirmationBox("Are you sure you want to purchase this coupon ?",
                    "Confirmation: Purchase",
                    "Stud'Easy - Confirmation")){
                try {
                    // We ask the facade to buy the coupon
                    FACADE.buyCoupon(coupon);
                    // We redirect
                    ROUTER.changeView(CouponRouter.COUPON_FXML_PATH,event);
                } catch (ErrorBuyCoupon errorBuyCoupon) {
                    // An error occurred while buying the coupon
                    errL.setTextFill(Paint.valueOf("red"));
                    errL.setText(errorBuyCoupon.getMessage());
                } catch (Exception e) {
                    errL.setTextFill(Paint.valueOf("red"));
                    errL.setText("An error occured, please try again later");
                }
            }
        }
        else{
            errL.setTextFill(Paint.valueOf("red"));
            errL.setText("You don't have enough points to buy this coupon");
        }
    }

    /**
     * Triggered when the user wants to delete the coupon
     * @param event the event triggered
     */
    public void deleteCoupon(ActionEvent event){
        if(AbstractRouter.confirmationBox("Are you sure you want to delete this coupon ?",
                "Confirmation: Deletion",
                "Stud'Easy - Confirmation")) {
            // We ask the facade to delete the coupon
            FACADE.deleteCoupon(coupon);
            // We redirect after the deletion
            try {
                ROUTER.changeView(CouponRouter.COUPON_FXML_PATH,event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Triggered when the user wants to update the coupon
     * @param event the event triggered
     */
    public void updateCoupon(ActionEvent event){
        try {
            ((CouponRouter)ROUTER).addUpdateCoupon(CouponRouter.ADD_UPDATE_COUPON_FXML_PATH,event,coupon,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggered when the user wants to go back
     * @param event the event triggered
     */
    public void cancel(ActionEvent event){
        try{
            ROUTER.changeView(CouponRouter.COUPON_FXML_PATH,event);
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
        // We initialize the texts to display
        this.titleT.setText(coupon.getTitle());
        this.descriptionCouponTA.setText(coupon.getDescription());
        this.costT.setText(Integer.toString(coupon.getValue()));
        this.quantityT.setText(Integer.toString(coupon.getQuantity()));
        User owner = coupon.getOwner();
        String company = ((RolePartner)owner.getRole()).getCompany();
        this.ownerT.setText(company);
        // The image according to the availability
        Image img;
        if(coupon.getQuantity() <= 0){
            img = new Image("images/coupon/notAvailable.png");
        }
        else{
            img = new Image("images/coupon/available.png");
        }
        this.availabilityImage.setImage(img);

        Session session = Session.getInstance();
        if(session.isAdmin()){
            updateB.setVisible(true);
            deleteB.setVisible(true);
        }
        else {
            // We retrieve the current user
            User currentUser = session.getCurrentUser();
            // To see how many points he has
            int pointsUser = ((RoleStudent)currentUser.getRole()).getPoints();
            this.pointsUserT.setText(Integer.toString(pointsUser));
            this.pointsUser = pointsUser;
            this.yourPointsL.setVisible(true);
            if (coupon.getQuantity() > 0) {
                buyB.setVisible(true);
            }
        }
    }
}
