package com.ameren.eis.integration_demo.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import com.ameren.eis.integration_demo.config.AmqConfiguration;
import com.ameren.eis.integration_demo.model.UserEventMessage;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

@ExtendWith(MockitoExtension.class)
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
    void testSend() throws JMSException {
        // Arrange
        UserEventMessage uem = new UserEventMessage("test", 0.0d, false);

        Message message = executeAndCapturePostProcessor(uem, AmqConfiguration.USER_QUEUE, sender::send);

        // Assert
        verify(message).setJMSCorrelationID(anyString());
        verify(message).setDoubleProperty("RatingFilter", 0.0d);
        verify(message).setBooleanProperty("ActiveFilter", false);
    }

    @Test
    void testSendTopic() throws JMSException {
        //Arrange
        UserEventMessage uem = new UserEventMessage("test", 0.0d, false);
        
        //Act
        Message message = executeAndCapturePostProcessor(uem, AmqConfiguration.PUB_TOPIC, sender::sendTopic);
        verify(message).setJMSCorrelationID(anyString());
        verify(message).setDoubleProperty("RatingFilter", 0.0d);
        verify(message).setBooleanProperty("ActiveFilter", false);
    }

    private Message executeAndCapturePostProcessor(UserEventMessage uem, String destination, Consumer<UserEventMessage> sendMethod) throws JMSException {
        
        sendMethod.accept(uem);
        
        // Capture the MessagePostProcessor
        ArgumentCaptor<MessagePostProcessor> captor = ArgumentCaptor.forClass(MessagePostProcessor.class);

        verify(jmsTemplate).convertAndSend(
                eq(destination),
                eq(uem),
                captor.capture());

        // Execute the lambda
        Message message = mock(Message.class);
        Message result = captor.getValue().postProcessMessage(message);

        // Assert
        assertSame(message, result);
        return message;
    }
}
