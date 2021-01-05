package com.github.studeasy.logic.facades;

import com.github.studeasy.dao.notificationDAO.NotificationDAO;
import com.github.studeasy.logic.common.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestNotificationFacade{

    //Mock the DAO
    @Mock
    private NotificationDAO notificationDAO;

    //Mock the connection
    @Mock
    Connection connection;

    @Mock
    PreparedStatement stmt;

    //Inject mock to the facade
    @InjectMocks
    private FacadeNotification facade;



    @BeforeEach
    public void setUp() throws SQLException {
        //instantiate the facade
        facade = FacadeNotification.getInstance();
        //success for query
        when(stmt.executeUpdate()).thenReturn(1);
        //simulate creation of query
        when(connection.prepareStatement(eq("INSERT INTO notification(ownerNotification,titleNotification,descriptionNotification) VALUES(?,?,?)"))).thenReturn(stmt);



    }

    @Test
    public void TestCreateNotification() throws Exception {
        //gets dao instance from facade and store it into mocked variable
        ReflectionTestUtils.setField(facade, "DAO", notificationDAO);

        //Mockito.doNothing().when(notificationDAO).createNotification(anyInt(),anyString(),anyString());
        //simulates behavior for mocked void method createNotification of DAO
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) throws Exception {
                Object[] params = invocation.getArguments();
                stmt = connection.prepareStatement("INSERT INTO notification(ownerNotification,titleNotification,descriptionNotification) VALUES(?,?,?)");
                stmt.setInt(1,(Integer)params[0]);
                stmt.setString(2, (String) params[1]);
                stmt.setString(3,(String) params[2]);
                int result = stmt.executeUpdate();
                if(result != 1){
                    throw new Exception();
                }
                return null;
            }
        }).when(notificationDAO).createNotification(any(Integer.class),any(String.class),any(String.class));

        //launch method to test
        facade.createNotification(1,"a","b");
        //checks is the DAO runs the method
        Mockito.verify(notificationDAO).createNotification(anyInt(),anyString(),anyString());
        //check if the query is executed
        Mockito.verify(stmt).executeUpdate();
    }
}
