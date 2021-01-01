package com.github.studeasy.gui.controller.coupon;

import com.github.studeasy.gui.routers.AbstractRouter;
import com.github.studeasy.gui.routers.CouponRouter;
import com.github.studeasy.gui.routers.UserRouter;
import com.github.studeasy.logic.common.*;
import com.github.studeasy.logic.common.role.RolePartner;
import com.github.studeasy.logic.common.role.RoleStudent;
import com.github.studeasy.logic.facades.FacadeCoupon;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
     * The list of the coupons displayed in the table
     */
    protected ObservableList<Coupon> couponsList;

    /**
     * The table view displaying the coupons
     */
    @FXML
    private TableView couponsTV;

    /**
     * The column displaying the titles
     */
    @FXML
    private TableColumn titleC;

    /**
     * The column displaying the costs
     */
    @FXML
    private TableColumn costC;

    /**
     * The column displaying the quantities
     */
    @FXML
    private TableColumn quantityC;

    /**
     * The column displaying the companies
     */
    @FXML
    private TableColumn<Coupon,String> companyC;

    /**
     * The column displaying the availability
     */
    @FXML
    private TableColumn<Coupon,String> availabilityC;

    /**
     * The column displaying the availability image
     */
    @FXML
    private TableColumn<Coupon, Image> availabilityImageC;

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
     * Triggered when the user types text in the search text field
     * @param observable
     * @param oldValue
     * @param newValue the new value entered by the user
     * @param filteredData the data filtered
     */
    public void filterCoupons(ObservableValue<? extends String> observable, String oldValue, String newValue, FilteredList<Coupon> filteredData) {
        // We check for each service
        filteredData.setPredicate(coupon -> {
                // If the text field is empty, we display all the coupons
                if ((newValue == null || newValue.isEmpty())) {
                    return true;
                }
                // We compare what is entered (in lower case) with the company of the owner
                String lowerCaseFilter = newValue.toLowerCase();
                User owner = coupon.getOwner();
                String company = ((RolePartner)owner.getRole()).getCompany();
                // If the filter matches the name of the company
                if (company.toLowerCase().contains(lowerCaseFilter)){
                    // The coupon is displayed
                    return true;
                }
                else {
                    // The company doesn't match, not displayed
                    return false;
                }
        });
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
     * Triggered when a user wants to see the details of a coupon
     * @param event the event triggered
     * @param coupon the coupon the user wants to see
     */
    private void viewCoupon(MouseEvent event, Coupon coupon) {
        try {
            ((CouponRouter)ROUTER).seeCoupon(CouponRouter.SEE_COUPON_FXML_PATH,event,coupon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to add each image on each row
     */
    public void addImageColumn(){
        Callback<TableColumn<Coupon, Image>, TableCell<Coupon, Image>> cellFactory = new Callback<>() {
            // For each row of the table view, we want to have the image of the availability
            @Override
            public TableCell<Coupon, Image> call(final TableColumn<Coupon, Image> param) {
                final TableCell<Coupon, Image> cell = new TableCell<Coupon, Image>() {
                    @Override
                    public void updateItem(Image item, boolean empty) {
                        super.updateItem(item, empty);
                        ImageView imageDisplayed = new ImageView(item);
                        setGraphic(imageDisplayed);
                    }
                };
                return cell;
            }
        };
        // We associate the cells to the table column
        availabilityImageC.setCellFactory(cellFactory);
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
        // In case we don't have any coupons
        couponsTV.setPlaceholder(new Label("There is no coupon to display"));
        // We retrieve all the coupons and put them in an observable list
        couponsList = FXCollections.observableArrayList(FACADE.getCoupons());

        // We put the titles of the coupons on the right column
        titleC.setCellValueFactory(
                new PropertyValueFactory<Coupon,String>("title")
        );
        // Same for the cost
        costC.setCellValueFactory(
                new PropertyValueFactory<Coupon,Integer>("value")
        );
        // Same for the quantity
        quantityC.setCellValueFactory(
                new PropertyValueFactory<Coupon,Integer>("quantity")
        );

        // We tell if the coupon is available
        availabilityC.setCellValueFactory(c -> {
            Coupon coupon = c.getValue();
            if(coupon.getQuantity() <= 0) {
                // Not available
                return new SimpleObjectProperty<>("Not available");
            }
            else{
                // available
                return new SimpleObjectProperty<>("Available");
            }
        });

        Image notAvailablePNG = new Image("images/coupon/notAvailable.png");
        Image availablePNG = new Image("images/coupon/available.png");

        // We associate an image to the availability of the coupon
        availabilityImageC.setCellValueFactory(c -> {
            Coupon coupon = c.getValue();
            if(coupon.getQuantity() <= 0) {
                // Not available image
                return new SimpleObjectProperty<>(notAvailablePNG);
            }
            else{
                // available image
                return new SimpleObjectProperty<>(availablePNG);
            }
        });

        // We add the images
        this.addImageColumn();

        // The company proposing the coupons
        companyC.setCellValueFactory(c -> {
            // We retrieve the service
            Coupon coupon = c.getValue();
            User owner = coupon.getOwner();
            String company  = ((RolePartner)owner.getRole()).getCompany();
            return new SimpleObjectProperty<>(company);
        });

        Session session = Session.getInstance();
        if(session.isAdmin()){
            // The admin can add coupons
            addCouponB.setVisible(true);
        }
        else{
            // We retrieve the current user
            User currentUser = session.getCurrentUser();
            // To see how many points he has
            int pointsUser = ((RoleStudent)currentUser.getRole()).getPoints();
            this.pointsUserT.setText(Integer.toString(pointsUser));
            this.yourPointsL.setVisible(true);
        }

        // Put a listener (double click) on each row to go to the information of a coupon
        couponsTV.setRowFactory( tv -> {
            TableRow<Coupon> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Coupon rowData = row.getItem();
                    this.viewCoupon(event,rowData);
                }
            });
            return row ;
        });

        // We create a filtered list containing the data
        FilteredList<Coupon> filteredCoupons = new FilteredList<>(couponsList, p -> true);
        // We associate the text field with a function listening to what is entered (to filter data)
        filterCouponTF.textProperty().addListener((observable,oldValue,newValue) -> this.filterCoupons(observable,oldValue,newValue,filteredCoupons));
        // We create a sorted list based on our filtered data
        SortedList<Coupon> sortedData = new SortedList<>(filteredCoupons);
        sortedData.comparatorProperty().bind(couponsTV.comparatorProperty());
        // We add the sorted data in the table
        couponsTV.setItems(sortedData);
    }
}
