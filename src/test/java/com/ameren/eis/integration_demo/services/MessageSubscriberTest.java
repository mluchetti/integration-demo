package com.ameren.eis.integration_demo.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.repository.UserEvent;
import com.ameren.eis.integration_demo.repository.UserEventRepository;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;

class MessageSubscriberTest {

    @Mock
    private UserEventRepository userEventRepository;
    @Mock
    private TextMessage textMessage;

    @InjectMocks
    private MessageSubscriber subscriber;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessMessage() {
        //Arrange
        UserEvent ue = new UserEvent("unittest@mail.com", 0d, false);        
        when(userEventRepository.save(any(UserEvent.class))).thenReturn(ue);

        //Act
        subscriber.processMessage(new UserEventMessage(ue.getEmail(), ue.getRating(), ue.isActive()), textMessage);
        ArgumentCaptor<UserEvent> captor = ArgumentCaptor.forClass(UserEvent.class);
        
        //Assert
        verify(userEventRepository).save(captor.capture());
        assertEquals("unittest@mail.com", captor.getValue().getEmail());
        
    }

    @Test
    void testProcessMessageException() throws Exception {

        when(textMessage.getJMSCorrelationID())
                .thenThrow(new JMSException("Boom"));

        assertDoesNotThrow(() -> subscriber.processMessage(
                new UserEventMessage(),
                textMessage));
    }
}
