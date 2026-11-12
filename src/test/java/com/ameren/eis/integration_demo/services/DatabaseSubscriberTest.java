package com.ameren.eis.integration_demo.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.repository.UserEvent;
import com.ameren.eis.integration_demo.repository.UserEventRepository;

import jakarta.jms.TextMessage;

class DatabaseSubscriberTest {

    @Mock
    private UserEventRepository userEventRepository;
    @Mock
    private TextMessage textMessage;

    @InjectMocks
    private DatabaseSubscriber subscriber;

    private UserEvent ue;
    private ArrayList<UserEvent> list;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ue = new UserEvent("unittest@mail.com", 0d, false);
        list = new ArrayList<UserEvent>();
        list.clear();
    }

    @Test
    void testProcessDatabaseSubscriber_whenEmailAlreadyExists_updateRating() {
        // Arrange
        list.add(ue);
        when(userEventRepository.findByEmail(ue.getEmail())).thenReturn(list);
        when(userEventRepository.save(any(UserEvent.class))).thenReturn(ue);

        // Act - New rating and active status for an existing email
        UserEventMessage user = new UserEventMessage(ue.getEmail(), 5.0d, true);
        subscriber.processDatabaseSubscriber(user, textMessage);

        ArgumentCaptor<UserEvent> captor = ArgumentCaptor.forClass(UserEvent.class);

        // Assert
        verify(userEventRepository).save(captor.capture());
        assertEquals(ue.getEmail(), captor.getValue().getEmail());
    }

    @Test
    void testProcessDatabaseSubscriber_whenEmailNotExists_addRating() {
        // Arrange
        when(userEventRepository.findByEmail(ue.getEmail())).thenReturn(list);
        when(userEventRepository.save(any(UserEvent.class))).thenReturn(ue);

        // Act
        UserEventMessage user = new UserEventMessage(ue.getEmail(), 3.5d, true);
        subscriber.processDatabaseSubscriber(user, textMessage);
        
        ArgumentCaptor<UserEvent> captor = ArgumentCaptor.forClass(UserEvent.class);

        // Assert
        verify(userEventRepository).save(captor.capture());
        assertEquals(ue.getEmail(), captor.getValue().getEmail());

    }

    @Test
    void testProcessDatabaseSubscriber_whenException() {

        UserEventMessage user = new UserEventMessage("unittest@mail.com", 5.0d, true);

        when(userEventRepository.findByEmail(anyString()))
                .thenThrow(new RuntimeException("Database error"));

        assertDoesNotThrow(() -> subscriber.processDatabaseSubscriber(user, textMessage));

        verify(userEventRepository).findByEmail("unittest@mail.com");
    }
}
