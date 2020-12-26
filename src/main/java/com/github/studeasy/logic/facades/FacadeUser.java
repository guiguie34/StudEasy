package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.exceptions.BadCredentialsException;
import com.github.studeasy.dao.userDAO.UserDAO;
import com.github.studeasy.logic.common.Session;
import com.github.studeasy.logic.common.User;

/**
 * The Facade User for the UserDAO
 * It contains methods that allow a user to login
 */
public class FacadeUser {

    /**
     * Singleton of the facade user
     */
    private static FacadeUser facadeUser = null;

    /**
     * The DAO connected to the database
     */
    private final UserDAO DAO;

    /**
     * Constructor of singleton FacadeUser
     * Instantiate the factory
     */
    private FacadeUser() {
        // We retrieve the UserDao
        this.DAO = UserDAO.getInstance();
    }

    /**
     * Static function that allow to get the instance of the FacadeUser
     * @return the instance of FacadeUser
     */
    public static FacadeUser getInstance(){
        if(facadeUser == null){
            facadeUser = new FacadeUser();
        }
        return facadeUser;
    }

    /**
     * Function login allows to check the password of a user,
     * if it's the right password set the user as the currentUser
     * @param email the mail of the user
     * @param password the password of the user
     * @throws Exception if the password or the email is wrong
     */
    public void  login(String email, String password) throws Exception {
        User u;
        Session sessionUser = Session.getInstance();
        u = DAO.searchUser(email);
        if(u.getPassword().equals(password)) { //change with hash password comparison
            sessionUser.setCurrentUser(u);
        }
        else{
            throw new BadCredentialsException("Bad Password");
        }
    }

    /***
     * Add points for a user
     * @param nbPoints
     * @param user
     * @throws Exception
     */

    public void addPoints(int nbPoints,User user) throws Exception {
        Session sessionUser = Session.getInstance();
        if(user.equals(sessionUser.getCurrentUser())){
            DAO.addPoints(nbPoints, user);
        }
        else throw new Exception("Permission deny");
    }

    /***
     * Remove points of a user
     * @param nbPoints
     * @param user
     * @throws Exception
     */
    public void removePoints(int nbPoints,User user) throws Exception{
        Session sessionUser = Session.getInstance();
        if(user.equals(sessionUser.getCurrentUser())){
            DAO.removePoints(nbPoints, user);
        }
        else throw new Exception("Permission deny");
    }

    /***
     * View number of points for a user
     * @param user
     * @return points
     * @throws Exception
     */
    public int viewPoints(User user) throws Exception{
        Session sessionUser = Session.getInstance();
        int points = 0;
        if(user.equals(sessionUser.getCurrentUser())){
            points=DAO.viewPoints(user);
        }
        else throw new Exception("Permission deny");
        return points;
    }

}