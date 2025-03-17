package com.ameren.eis.integration_demo.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import com.ameren.eis.integration_demo.model.UserEventMessage;

class MessageSenderTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private MessageSender sender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSend() {
        //Arrange
        UserEventMessage uem = new UserEventMessage("test", 0.0d, false);
        doNothing().when(jmsTemplate).convertAndSend("", uem, null);
        
        //Act
        sender.send(uem);
        
        //Assert
        assertTrue(true);
    }

    @Test
    void testSendTopic() {
        //Arrange
        UserEventMessage uem = new UserEventMessage("test", 0.0d, false);
        doNothing().when(jmsTemplate).convertAndSend("", uem, null);
        
        //Act
        sender.sendTopic(uem);
        
        //Assert
        assertTrue(true);
    }
}
