package com.github.studeasy.dao.serviceDAO;

import com.github.studeasy.logic.common.CategoryTag;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.sql.*;
import java.util.ArrayList;
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
     * Set to default the category of the service
     */
    public void setDefaultCategory(){
        // We prepare the SQL request to update the category
        PreparedStatement preparedStatement;
        String request = "UPDATE service SET fkCategory = 1 WHERE fkCategory IS NULL";
        try {
            preparedStatement = DB.prepareStatement(request);
            // We execute the query
            preparedStatement.executeUpdate();
        }
        // Error with the database
        catch (SQLException err) {
            err.printStackTrace();
        }
    }

    /**
     * Retrieve the services of the user
     * @param currentUser the user wanting to see his services
     * @return the services of the user
     */
    public ArrayList<Service> getMyServices(User currentUser){
        ArrayList<Service> servicesList = new ArrayList<>();
        try {
            // We prepare the SQL request to retrieve the services of the user
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            String request = "SELECT * FROM service, categorytag " +
                             "WHERE ownerService = ? " +
                             "AND fkCategory = categorytag.idCategory " +
                             "ORDER BY dateCreationService DESC";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, currentUser.getIdUser());
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We retrieve all the existing services
            while (resultSet.next()) {
                // We need to retrieve the category linked to the service
                CategoryTag categoryS = new CategoryTag(resultSet.getInt(2),resultSet.getString(11),resultSet.getString(12));
                // We create the service
                Date dateCreation = resultSet.getTimestamp(6);
                Service service = new Service(resultSet.getInt(1),resultSet.getString(3),
                        resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(7),
                        currentUser,categoryS,resultSet.getInt(8),dateCreation);
                // And put it with the others
                servicesList.add(service);
            }
        }
        // Error with the database
        catch(SQLException err){
            err.printStackTrace();
        }
        return servicesList;
    }

    /**
     * Retrieve all the pending services
     * @return the pending services
     */
    public ArrayList<Service> getPendingServices() {
        String request = "SELECT * FROM service, categorytag, user " +
                    "WHERE stateService = 0 " +
                    "AND fkCategory = categorytag.idCategory " +
                    "AND ownerService = user.idUser " +
                    "ORDER BY dateCreationService ASC";
        return getServices(request);
    }

    /**
     * Retrieve all the services
     * @return the services
     */
    public ArrayList<Service> getOnlineServices(){
        String request = "SELECT * FROM service, categorytag, user " +
                "WHERE stateService = 1 " +
                "AND fkCategory = categorytag.idCategory " +
                "AND ownerService = user.idUser " +
                "ORDER BY dateCreationService DESC";
        return this.getServices(request);
    }

    /**
     * Function used to get services through a SQL request
     * @param request the SQL request
     * @return a list of services
     */
    private ArrayList<Service> getServices(String request){
        ArrayList<Service> servicesList = new ArrayList<>();
        try {
            // We prepare the SQL request to retrieve the pending services
            PreparedStatement preparedStatement;
            // Will contain the result of the query
            ResultSet resultSet;
            preparedStatement = DB.prepareStatement(request);
            // We execute the query
            resultSet = preparedStatement.executeQuery();
            // We retrieve all the existing services
            while (resultSet.next()) {
                // We need to retrieve the category linked to the service
                CategoryTag categoryS = new CategoryTag(resultSet.getInt(2),resultSet.getString(11),resultSet.getString(12));
                // We also need the user
                User owner = new User(resultSet.getInt(9),resultSet.getString(15),resultSet.getString(14),
                        resultSet.getString(18),resultSet.getString(17),resultSet.getInt(16),resultSet.getString(20),
                        resultSet.getString(19),resultSet.getInt(21),resultSet.getString(22));
                // We create the service
                Date dateCreation = resultSet.getTimestamp(6);
                Service service = new Service(resultSet.getInt(1),resultSet.getString(3),
                        resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(7),
                        owner,categoryS,resultSet.getInt(8),dateCreation);
                // And put it with the others
                servicesList.add(service);
            }
        }
        // Error with the database
        catch(SQLException err){
            err.printStackTrace();
        }
        return servicesList;
    }

    /**
     * Delete the service
     * @param service the service to delete
     */
    public void deleteService(Service service){
        // We prepare the SQL request to delete a service
        PreparedStatement preparedStatement;
        String request = "DELETE FROM service WHERE idService = ?";
        try {
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, service.getIdService());
            // We execute the query
            preparedStatement.executeUpdate();
        }
        // Error with the database
        catch (SQLException err) {
            err.printStackTrace();
        }
    }

    /**
     * Validate the service
     * @param service the service to validate
     */
    public void validateService(Service service){
        // We prepare the SQL request to validate a service
        PreparedStatement preparedStatement;
        String request = "UPDATE service SET stateService = 1 WHERE idService = ?";
        try {
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, service.getIdService());
            // We execute the query
            preparedStatement.executeUpdate();
        }
        // Error with the database
        catch (SQLException err) {
            err.printStackTrace();
        }
    }

    /**
     * Create a service with those information
     * @param titleS the title of the new service
     * @param descriptionS the description of the new service
     * @param category the category associated to the new service
     * @param cost the cost of the new service
     * @param typeS the type of the service
     * @param user the user creating the service
     */
    public void submitService(String titleS, String descriptionS, CategoryTag category,
                                       int cost, int typeS, User user){
        // We prepare the SQL request to insert a service
        PreparedStatement preparedStatement;
        String request = "INSERT INTO service (titleService,descriptionService," +
                "costService,typeService,fkCategory, ownerService) VALUES  (?,?,?,?,?,?)";
        try {
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, titleS);
            preparedStatement.setString(2, descriptionS);
            preparedStatement.setInt(3, cost);
            preparedStatement.setInt(4,typeS);
            preparedStatement.setInt(5, category.getIdCat());
            preparedStatement.setInt(6, user.getIdUser());
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
