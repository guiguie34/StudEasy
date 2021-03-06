package com.github.studeasy.dao.couponDAO;

import com.github.studeasy.logic.common.Coupon;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.util.ArrayList;

/**
 * Abstract class for the coupon DAO
 * Contains the methods needed by the coupon DAO
 */
public abstract class CouponDAO {

    /**
     * The singleton of the CouponDAO
     */
    private static CouponDAO couponDAO = null;

    /**
     * Static method which returns the instance of the CategoryDAO,
     * or ask the factory to create one
     * @return the instance of MySQLCategoryDAO
     */
    public static CouponDAO getInstance(){
        if (couponDAO == null){
            Factory factory = Factory.getInstance();
            couponDAO = factory.createCouponDAO();
        }
        return couponDAO;
    }

    /**
     * Add the coupon in the DB
     * @param titleCoupon the title of the coupon
     * @param descriptionCoupon the description of the coupon
     * @param owner the partner proposing the coupons
     * @param quantityCoupons the number of coupons available
     * @param costCoupon the price in points of the coupon
     */
    public abstract void addCoupon(String titleCoupon, String descriptionCoupon, User owner, int quantityCoupons, int costCoupon);

    /**
     * Update the coupon in the DB
     * @param titleCoupon the title of the coupon
     * @param descriptionCoupon the description of the coupon
     * @param owner the partner proposing the coupons
     * @param quantityCoupons the number of coupons available
     * @param costCoupon the price in points of the coupon
     * @param couponToUpdate the coupon to update
     */
    public abstract void updateCoupon(String titleCoupon, String descriptionCoupon, User owner, int quantityCoupons, int costCoupon, Coupon couponToUpdate);

    /**
     * Delete the coupon
     * @param couponToDelete the coupon to delete
     */
    public abstract void deleteCoupon(Coupon couponToDelete);

    /**
     * Retrieve all the coupons from the database
     * @return all the coupons
     */
    public abstract ArrayList<Coupon> getCoupons();

    /**
     * Decrease the quantity of 1
     * @param coupon the coupon to update
     * @throws Exception if an error occurs
     */
    public abstract void decreaseQuantityCoupon(Coupon coupon) throws Exception;

    /**
     * Increase the quantity of 1
     * @param coupon the coupon to update
     * @throws Exception if an error occurs
     */
    public abstract void increaseQuantityCoupon(Coupon coupon) throws Exception;
}
