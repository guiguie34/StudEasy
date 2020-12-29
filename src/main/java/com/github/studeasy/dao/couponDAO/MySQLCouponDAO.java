package com.github.studeasy.dao.couponDAO;

import com.github.studeasy.logic.common.Coupon;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    /**
     * Retrieve all the coupons from the database
     * @return all the coupons
     */
    public ArrayList<Coupon> getCoupons() {
        ArrayList<Coupon> couponsList = new ArrayList<>();
        try {
            // We prepare the SQL request to retrieve the pending services
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM coupon, user " +
                    "WHERE coupon.ownerCoupon = user.idUser " +
                    "ORDER BY quantityCoupon DESC";
            preparedStatement = DB.prepareStatement(request);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We retrieve all the existing services
            while (resultSet.next()) {
                // We need the partner
                User owner = new User(resultSet.getInt(7),resultSet.getString(9),resultSet.getString(8),
                        resultSet.getString(12),resultSet.getString(11),resultSet.getInt(10),resultSet.getString(14),
                        resultSet.getString(13),resultSet.getInt(15),resultSet.getString(16));
                // We create the coupon
                Coupon coupon = new Coupon(resultSet.getInt(1),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(6),owner);
                // And put it with the others
                couponsList.add(coupon);
            }
        }
        // Error with the database
        catch(SQLException err){
            err.printStackTrace();
        }
        return couponsList;
    }
}
