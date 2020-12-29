package com.github.studeasy.gui.routers;

import com.github.studeasy.logic.facades.FacadeNotification;

/**
 * Router for the notification's routes
 */
public class NotificationRouter extends AbstractRouter{
    private NotificationRouter(){

    }

    private static class lazyLoader{
        private static final NotificationRouter INSTANCE = new NotificationRouter();
    }

    public static NotificationRouter getInstance(){
        return lazyLoader.INSTANCE;
    }
}
