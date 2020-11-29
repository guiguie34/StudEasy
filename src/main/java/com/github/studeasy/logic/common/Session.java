package com.github.studeasy.logic.common;

import com.github.studeasy.dao.db.MySQLConnectionUtil;

public class Session {



    private User currentUser;

    private Session(){

    }

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
}
