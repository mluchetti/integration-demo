package com.ameren.eis.integration_demo.services.http;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.services.MessageSender;

class UserServiceTest {

    @Mock
    private MessageSender sender;
    @InjectMocks
    private UserServiceImpl us;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPublishEvent() {
        //Arrange
        UserEventMessage uem = new UserEventMessage();
        doNothing().when(sender).sendTopic(uem);
        
        //Act
        us.createEvent(uem);
        
        //Assert
        verify(sender, atLeastOnce()).sendTopic(uem);
    }

    @Test
    void testPublishMessage() {
        //Arrange
        UserEventMessage uem = new UserEventMessage();
        doNothing().when(sender).send(uem);
        
        //Act
        us.createMessage(uem);

        //Assert
        verify(sender, atLeastOnce()).send(uem);
    }
}
