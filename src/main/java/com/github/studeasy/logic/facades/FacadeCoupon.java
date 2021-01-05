package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.couponDAO.CouponDAO;
import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Coupon;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.common.role.RolePartner;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;
import com.github.studeasy.logic.facades.exceptions.ErrorBuyCoupon;
import com.github.studeasy.logic.utils.KeyGen;
import com.github.studeasy.logic.utils.Mail;

import java.util.ArrayList;

/**
 * The coupon facade
 * It contains the methods coupon related
 */
public class FacadeCoupon {

    /**
     * Singleton of the facade coupon
     */
    private static FacadeCoupon facadeCoupon = null;

    /**
     * The DAO connected to the database
     */
    private final CouponDAO couponDAO;

    /**
     * The DAO connected to the database
     */
    private final UserDAO userDAO;

    /**
     * Constructor of singleton FacadeCoupon
     * Instantiate the factory
     */
    private FacadeCoupon() {
        // We retrieve the CouponDAO
        this.couponDAO = CouponDAO.getInstance();
        this.userDAO = UserDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeCoupon
     * @return the instance of facadeCoupon
     */
    public static FacadeCoupon getInstance(){
        if(facadeCoupon == null){
            facadeCoupon = new FacadeCoupon();
        }
        return facadeCoupon;
    }

    /**
     * Check if the coupon can be updated, and update it if possible
     * @param titleCoupon the title of the coupon
     * @param descriptionCoupon the description of the coupon
     * @param owner the partner proposing the coupons
     * @param quantityCoupons the number of coupons available
     * @param costCoupon the price in points of the coupon
     * @param coupon the coupon to update
     * @throws BadInformationException if we can't add the coupon
     */
    public void updateCoupon(String titleCoupon, String descriptionCoupon, User owner, int quantityCoupons, int costCoupon, Coupon coupon) throws BadInformationException{
        // We check if the title and the description have correct sizes
        if((titleCoupon.length() >= 2 && titleCoupon.length() <= 25) && descriptionCoupon.length() <= 500){
            // We ask the DAO to update the coupon
            couponDAO.updateCoupon(titleCoupon,descriptionCoupon,owner,quantityCoupons,costCoupon,coupon);
        }
        else{
            // We tell the user what's wrong
            throw new BadInformationException("The title and description do not have a correct length");
        }
    }

    /**
     * Check if the coupon can be added, and add it if possible
     * @param titleCoupon the title of the coupon
     * @param descriptionCoupon the description of the coupon
     * @param owner the partner proposing the coupons
     * @param quantityCoupons the number of coupons available
     * @param costCoupon the price in points of the coupon
     * @throws BadInformationException if we can't add the coupon
     */
    public void addCoupon(String titleCoupon, String descriptionCoupon, User owner, int quantityCoupons, int costCoupon) throws BadInformationException {
        // We check if the title and the description have correct sizes
        if((titleCoupon.length() >= 2 && titleCoupon.length() <= 25) && descriptionCoupon.length() <= 500){
            // We ask the DAO to create the coupon
            couponDAO.addCoupon(titleCoupon,descriptionCoupon,owner,quantityCoupons,costCoupon);
        }
        else{
            // We tell the user what's wrong
            throw new BadInformationException("The title and description do not have a correct length");
        }
    }

    /**
     * Delete the coupon
     * @param couponToDelete the coupon to delete
     */
    public void deleteCoupon(Coupon couponToDelete){
        // We ask the DAO to delete the coupon
        couponDAO.deleteCoupon(couponToDelete);
    }

    /**
     * Used to buy a coupon
     * We decrease the amount of points of the user,
     * We decrease the quantity of coupons,
     * We send an email containing the code of the coupon
     * @param coupon the coupon bought
     * @throws ErrorBuyCoupon when an error occurs
     */
    public void buyCoupon(Coupon coupon) throws Exception {
        Session session = Session.getInstance();
        User currentUser = session.getCurrentUser();
        try {
            // First, the user spends his points
            userDAO.removePoints(coupon.getValue(),currentUser);
        } catch (Exception err) {
            throw new ErrorBuyCoupon("An error occurred, you might not have enough points");
        }
        try{
            // Now we decrease the quantity of the coupon
            couponDAO.decreaseQuantityCoupon(coupon);
        }
        catch (Exception err){
            // Transaction aborted, we give back the credits to the user
            userDAO.addPoints(coupon.getValue(),currentUser);
            throw new ErrorBuyCoupon("An error occurred, the coupon might not be available anymore");
        }
        try{
            // We now send the mail with the coupon to the user
            String subject = "Here is your coupon !";
            User owner = coupon.getOwner();
            String company = ((RolePartner) owner.getRole()).getCompany();
            String object = "Thank you, "+currentUser.getFirstname()+", for your purchase !\n\n" +
                    "Here is the coupon you just bought : \n\n\t"+KeyGen.generateKey()+"\n\n" +
                    "You can now use your coupon on "+company+"'s website.";
            Mail.sendMail(subject,object,currentUser.getEmailAddress());
        }
        catch(Exception err){
            // Transaction aborted, we give back the credits to the user
            userDAO.addPoints(coupon.getValue(),currentUser);
            // We increase back the quantity of the coupon
            couponDAO.increaseQuantityCoupon(coupon);
            throw new ErrorBuyCoupon("An error occurred, we were not able to send you the email");
        }
        finally {
            // We update the current user (in case its points changed)
            User u = userDAO.searchUser(currentUser.getEmailAddress());
            session.setCurrentUser(u);
        }
    }

    /**
     * Retrieve all the coupons from the database
     * @return all the coupons
     */
    public ArrayList<Coupon> getCoupons() {
        // We ask the DAO to retrieve the coupons
        return couponDAO.getCoupons();
    }
}
