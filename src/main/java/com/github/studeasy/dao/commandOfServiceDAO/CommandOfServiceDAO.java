package com.github.studeasy.dao.commandOfServiceDAO;

import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.factory.Factory;

import java.util.ArrayList;

/**
 * Abstract class for the Command Of Service DAO
 * Contains the methods needed by the Command Of Service DAO
 */
public abstract class CommandOfServiceDAO {
    /**
     * The singleton of the CommandOfServiceDAO
     */
    private static CommandOfServiceDAO csDAO = null;

    /**
     * Static method which returns the instance of the CommandOfServiceDAO,
     * or ask the factory to create one
     * @return The instance of MySQLCommandOfServiceDAO
     */
    public static CommandOfServiceDAO getInstance(){
        if (csDAO == null){
            Factory factory = Factory.getInstance();
            csDAO = factory.createCommandOfServiceDAO();
        }
        return csDAO;
    }

    /***
     * Static method which allows to accept the transaction of a command
     * command will save to the database
     * @param c
     * @throws Exception
     */
    public abstract void acceptTransaction(CommandOfService c) throws Exception;

    /***
     * Static method which allows to decline the transaction of a command
     * @param c
     * @throws Exception
     */
    public abstract void declineTransaction(CommandOfService c) throws Exception;

    /***
     * Static method which allows user apply for a service
     * Record will be saved to the database if it's approved
     * @param s
     * @param currentUser
     * @throws Exception
     */
    public abstract void applyorbuyForService(Service s,Object currentUser) throws Exception;


    /***
     * Static method which allows user to add a feedback to a service
     * Feedback will be saved to the database
     * @param c
     * @throws Exception
     */
    public abstract void addFeedback(CommandOfService c) throws Exception;

    /***
     * Static method which allows to return the list of the command for a user
     * @param currentUser
     * @return List of the command
     * @throws Exception
     */
    public abstract ArrayList<CommandOfService> getServiceBought(User currentUser) throws Exception;

    /***
     * Static method which allows to return the list of the command pending for a user
     * @param currentUser
     * @return
     * @throws Exception
     */
    public abstract ArrayList<CommandOfService> getMyServicePending(User currentUser) throws Exception;

}
