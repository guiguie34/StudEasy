package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.commandOfServiceDAO.CommandOfServiceDAO;
import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.CommandOfService;
import com.github.studeasy.logic.common.Service;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;
import com.github.studeasy.logic.facades.exceptions.ErrorCommand;

import java.util.ArrayList;

/**
 * The Facade User for the CommandOfServiceDAO
 * It contains methods that allow to create,update,delete a command
 */
public class FacadeCommandOfService {

    /**
     * Singleton of the facade CommandOfServie
     */
    private static FacadeCommandOfService facadeCommandOfService= null;

    /**
     * The DAO connected to the database
     */
    private final CommandOfServiceDAO DAO;

    /**
     * The DAO connected to the database
     */
    private final UserDAO userDAO;

    /**
     * Constructor of singleton FacadeCommandOfService
     * Instantiate the factory
     */
    private FacadeCommandOfService() {
        // We retrieve the UserDao
        this.DAO = CommandOfServiceDAO.getInstance();
        this.userDAO = UserDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeCommandOfService
     * @return the instance of FacadeCommandOfService
     */
    public static FacadeCommandOfService getInstance(){
        if(facadeCommandOfService == null){
            facadeCommandOfService = new FacadeCommandOfService();
        }
        return facadeCommandOfService;
    }

    /**
     * Retrieve the command of the current user
     * @return the command of the user
     */
    public ArrayList<CommandOfService> getMyCommand() throws Exception {
        // We retrieve the current user
        Session sessionUser = Session.getInstance();
        User currentUser = sessionUser.getCurrentUser();
        // We ask the DAO to retrieve the services of the user
        return DAO.getServiceBought(currentUser);
    }

    public ArrayList<CommandOfService> getMyCommandPending() throws Exception {
        // We retrieve the current user
        Session sessionUser = Session.getInstance();
        User currentUser = sessionUser.getCurrentUser();
        // We ask the DAO to retrieve the services of the user
        return DAO.getMyServicePending(currentUser);
    }

    /**
     * Methode that allows to accept a command by asking DAO
     * @param c
     * @throws Exception
     */
    public void acceptTransaction(CommandOfService c) throws Exception {
        Session session = Session.getInstance();
        User currentUser = session.getCurrentUser();
        // We pay with the points
        Service s = c.getService();
        User u = c.getOwner();
        if(s.getTypeService() == 1){
            // We pay the user helping
            userDAO.removePoints(s.getCost(),currentUser);
            userDAO.addPoints(s.getCost(),u);
        }
        else{
            // We pay the owner of the service
            userDAO.addPoints(s.getCost(),s.getOwner());
        }
        DAO.acceptTransaction(c);
        FacadeNotification facadeNotification = FacadeNotification.getInstance();
        String title = "Transaction accepted!";
        String desc = "Your command for: "+s.getTitle()+" has been accepted.\n" +
                "The points have been exchanged";
        try {
            facadeNotification.createNotification(c.getOwner().getIdUser(),title,desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that allows to delete a command by asking DAO
     * @param c
     * @throws Exception
     */
    public void declineTransaction(CommandOfService c) throws Exception {
        // We give back the points to the user
        Service s = c.getService();
        User u = c.getOwner();
        if(s.getTypeService() == 0){
            userDAO.addPoints(s.getCost(),u);
        }
        DAO.declineTransaction(c);
        FacadeNotification facadeNotification = FacadeNotification.getInstance();
        String title = "Transaction declined!";
        String desc = "Your command for: "+s.getTitle()+" has been declined.\n";
        try {
            facadeNotification.createNotification(u.getIdUser(),title,desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Methode allows to buy a service and save the record
     * @param s
     * @throws Exception
     */
    public void buyorapplyService(Service s, User u) throws Exception{
        // We check if there is already an existing command
        if(DAO.commandPending(s,u) == null){
            // We retrieve the points if it's a proposed service
            if(s.getTypeService() == 0){
                userDAO.removePoints(s.getCost(),u);
            }
            DAO.applyorbuyForService(s,u);
            FacadeNotification facadeNotification = FacadeNotification.getInstance();
            String title = "Command pending!";
            String desc = "Your command for: "+s.getTitle()+" is now pending.\n" +
                    "You will receive a notification telling you if the owner accepted " +
                    "or not the transaction.";
            try {
                facadeNotification.createNotification(u.getIdUser(),title,desc);
                title = "You have a command pending to check!";
                desc = "A command for: "+s.getTitle()+" is now pending.\n" +
                        "You can accept or decline it.";
                facadeNotification.createNotification(s.getOwner().getIdUser(),title,desc);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            throw new ErrorCommand("You already have a pending command for this service.");
        }
    }

    /**
     * Method allows to add a feedback to a service
     * @param c
     * @throws Exception
     */
    public void addFeedback(CommandOfService c) throws Exception {
        DAO.addFeedback(c);
    }

    /**
     * Decline all the commands linked to a service
     * @param service the service concerned
     */
    public void deleteAllCommands(Service service) throws Exception {
        ArrayList<CommandOfService> allCommands = DAO.getPendingCommandsOfOneService(service);
        for(CommandOfService command : allCommands) {
            this.declineTransaction(command);
        }

    }
}
