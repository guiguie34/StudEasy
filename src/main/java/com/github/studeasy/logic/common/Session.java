package com.github.studeasy.logic.common;

import com.github.studeasy.logic.common.role.*;

/**
 * Session will keep in memory all the information of an user when he will connect to the application
 */
public class Session implements SessionI {

    /**
     * Singleton of the session
     */
    private static Session session = null;

    /**
     * User kept in memory in the session
     */
    private User currentUser;

    /**
     * Private constructor for the singleton
     */
    private Session(){}

    /**
     * Get the unique session
     * @return the session
     */
    public static Session getInstance(){
        if(session == null){
            session = new Session();
        }
        return session;
    }

    /**
     * destroy the current session
     */
    public void disconnect(){

        currentUser =null;
    }

    /**
     * Get the current user in the session
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Used to change the current User
     * @param currentUser the new User
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Check if the current user is an admin
     * @return true if it's an admin, false otherwise
     */
    public boolean isAdmin(){
        return (currentUser.getRole() instanceof RoleAdmin);
    }

    /**
     * Check if the current user is a studebt
     * @return true if it's a student, false otherwise
     */
    public boolean isStudent(){
        return (currentUser.getRole() instanceof RoleStudent);
    }

    /**
     * Check if the current user is a partner
     * @return true if it's a partner, false otherwise
     */
    public boolean isPartner(){
        return (currentUser.getRole() instanceof RolePartner);
    }
}