package com.ameren.eis.integration_demo.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.repository.UserEvent;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;

class MessageFileSubscriberTest {

    @Mock
    private TextMessage textMessage;

    @InjectMocks
    private MessageFileSubscriber subscriber;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testProcessFileSubscriber() {

        // Arrange
        UserEvent ue = new UserEvent("unittest@mail.com", 0d, false);
        // when(userEventRepository.save(ue)).thenReturn(ue);

        // Act
        subscriber.processFileSubscriber(new UserEventMessage(ue.getEmail(), ue.getRating(), ue.isActive()),
                textMessage);

        // Assert
        assertTrue(true);

    }

    @Test
    void testProcessFileSubscriberException() throws Exception {

        when(textMessage.getJMSMessageID())
                .thenThrow(new JMSException("Boom"));

        assertDoesNotThrow(() -> subscriber.processFileSubscriber(
                new UserEventMessage(),
                textMessage));
    }
}
