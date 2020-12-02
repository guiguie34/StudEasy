package com.github.studeasy.logic.common;

import com.github.studeasy.logic.common.role.*;

/**
 * Session will keep in memory all the information of an user when he will connect to the application
 */
public class Session implements SessionI {

    /**
     * User kept in memory in the session
     */
    private User currentUser;

    public static class loadSession{
        public static final Session instance= new Session();
    }

    public static Session getInstance(){
        return Session.loadSession.instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isAdmin(){
        return (currentUser.getRole() instanceof RoleAdmin);
    }

    public boolean isStudent(){
        return (currentUser.getRole() instanceof RoleStudent);
    }

    public boolean isPartner(){
        return (currentUser.getRole() instanceof RolePartner);
    }
}