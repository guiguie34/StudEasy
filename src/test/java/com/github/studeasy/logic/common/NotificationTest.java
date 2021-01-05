package com.github.studeasy.logic.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    
    private Notification notification;


    @BeforeEach
    void setUp() {
        notification = new Notification(1,"title","description",false,null);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetTitle() {
        assertEquals( "title", notification.getTitle(),"Incorrect name");
    }

    @Test
    void testSetTitle() {
        notification.setTitle("new title");
        assertEquals( "new title", notification.getTitle(),"Incorrect name");

    }

}