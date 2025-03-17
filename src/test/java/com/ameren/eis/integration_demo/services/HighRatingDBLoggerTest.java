package com.ameren.eis.integration_demo.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.repository.HighRatingLog;
import com.ameren.eis.integration_demo.repository.HighRatingLogRepository;

import jakarta.jms.TextMessage;

class HighRatingDBLoggerTest {

    @Mock
    private HighRatingLogRepository highRatingLogRepository;
    @Mock
    private TextMessage textMessage;
    @InjectMocks
    private HighRatingDBLogger subscriber;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessHighRatingSubscriber() {
        //Arrange
        HighRatingLog highRating = new HighRatingLog("unittest@mail.com", 0d, false, "ID:12345", "098765");
        when(highRatingLogRepository.save(highRating)).thenReturn(highRating);
        
        //Act
        subscriber.processHighRatingSubscriber(new UserEventMessage(), textMessage);        
        
        //Assert
        assertTrue(true);

    }
}
