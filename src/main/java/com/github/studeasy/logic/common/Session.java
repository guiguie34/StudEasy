package com.github.studeasy.logic.common;

public class Session implements SessionI {

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
        return (currentUser.getRole() == 0);
    }

    public boolean isStudent(){
        return (currentUser.getRole() == 1);
    }

    public boolean isPartner(){
        return (currentUser.getRole() == 2);
    }
}