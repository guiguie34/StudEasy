package com.github.studeasy.dao.serviceDAO;

import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.factory.Factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The Service DAO using a MySQL database
 * Contains all the methods accessing service related data
 */
public class MySQLServiceDAO extends ServiceDAO {

    /**
     * The connection to the database
     */
    private final Connection DB;

    /**
     * Instantiate the connection db
     */
    public MySQLServiceDAO() {
        Factory connection = Factory.getInstance();
        this.DB = connection.getDb();
    }

    /**
     * Create a service with those information
     * @param titleS the title of the new service
     * @param descriptionS the description of the new service
     * @param category the category associated to the new service
     * @param cost the cost of the new service
     * @param typeS the type of the service
     * @param creationDate the date of creation
     */
    public void submitService(String titleS, String descriptionS, CategoryTag category,
                                       int cost, int typeS, Date creationDate){
        // We convert the date to the JDBC format
        Timestamp sDate = new Timestamp(creationDate.getTime());
        // We prepare the SQL request to insert a service
        PreparedStatement preparedStatement;
        String request = "INSERT INTO service (titleService,descriptionService," +
                "costService,typeService,fkCategory,dateCreationService) VALUES  (?,?,?,?,?,?)";
        try {
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, titleS);
            preparedStatement.setString(2, descriptionS);
            preparedStatement.setInt(3, cost);
            preparedStatement.setInt(4,typeS);
            preparedStatement.setInt(5, category.getIdCat());
            preparedStatement.setTimestamp(6, sDate);
            // We execute the query
            preparedStatement.executeUpdate();
            // The service is automatically in a pending state
        }
        // Error with the database
        catch (SQLException err) {
            err.printStackTrace();
        }
    }
}
