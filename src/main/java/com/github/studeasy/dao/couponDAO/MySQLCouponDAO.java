package com.github.studeasy.dao.couponDAO;

import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The Coupon DAO using a MySQL database
 * Contains all the methods accessing coupon related data
 */
public class MySQLCouponDAO extends CouponDAO{

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLCouponDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }

    /**
     * Add the coupon in the DB
     * @param titleCoupon the title of the coupon
     * @param descriptionCoupon the description of the coupon
     * @param owner the partner proposing the coupons
     * @param quantityCoupons the number of coupons available
     * @param costCoupon the price in points of the coupon
     */
    public void addCoupon(String titleCoupon, String descriptionCoupon, User owner, int quantityCoupons, int costCoupon) {
        // We prepare the SQL request to insert a coupon
        PreparedStatement preparedStatement;
        String request = "INSERT INTO coupon (titleCoupon,descriptionCoupon," +
                "valueCoupon,quantityCoupon,ownerCoupon) VALUES  (?,?,?,?,?)";
        try {
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, titleCoupon);
            preparedStatement.setString(2, descriptionCoupon);
            preparedStatement.setInt(3, costCoupon);
            preparedStatement.setInt(4,quantityCoupons);
            preparedStatement.setInt(5, owner.getIdUser());
            // We execute the query
            preparedStatement.executeUpdate();
        }
        // Error with the database
        catch (SQLException err) {
            err.printStackTrace();
        }
    }
}
