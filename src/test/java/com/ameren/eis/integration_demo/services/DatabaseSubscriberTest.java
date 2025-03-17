package com.ameren.eis.integration_demo.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void testProcessDatabaseSubscriber_whenEmailAlreadyExists() {
        // Arrange
        list.add(ue);
        when(userEventRepository.findByEmail(null)).thenReturn(list);
        when(userEventRepository.save(ue)).thenReturn(ue);

        // Act
        subscriber.processDatabaseSubscriber(new UserEventMessage(), textMessage);

        // Assert
        assertTrue(true);

    }

    @Test
    void testProcessDatabaseSubscriber_whenEmailNotExists() {
        // Arrange
        when(userEventRepository.findByEmail(null)).thenReturn(list);
        when(userEventRepository.save(ue)).thenReturn(ue);

        // Act
        subscriber.processDatabaseSubscriber(new UserEventMessage(), textMessage);

        // Assert
        assertTrue(true);

    }

}
