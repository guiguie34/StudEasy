package com.github.studeasy.dao.commandOfServiceDAO;

import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.factory.Factory;

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
     * @throws Exception
     */
    public abstract void applyForService(Service s) throws Exception;

    /***
     * Static method which allows user to buy a service
     * Record will be saved if it's approved
     * @param s
     * @throws Exception
     */
    public abstract void buyService(Service s) throws Exception;

    /***
     * Static method which allows user to add a feedback to a service
     * Feedback will be saved to the database
     * @param c
     * @throws Exception
     */
    public abstract void addFeedback(CommandOfService c) throws Exception;




}
