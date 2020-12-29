package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.couponDAO.CouponDAO;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.exceptions.BadInformationException;

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
    private final CouponDAO DAO;

    /**
     * Constructor of singleton FacadeCoupon
     * Instantiate the factory
     */
    private FacadeCoupon() {
        // We retrieve the CouponDAO
        this.DAO = CouponDAO.getInstance();
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
            DAO.addCoupon(titleCoupon,descriptionCoupon,owner,quantityCoupons,costCoupon);
        }
        else{
            // We tell the user what's wrong
            throw new BadInformationException("The title and description do not have a correct length");
        }
    }
}
