package com.ameren.eis.integration_demo.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ameren.eis.integration_demo.config.AmqConfiguration;
import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.repository.UserEvent;
import com.ameren.eis.integration_demo.repository.UserEventRepository;

import jakarta.jms.TextMessage;

@Service
public class DatabaseSubscriber {

    private static Logger logger = LoggerFactory.getLogger(DatabaseSubscriber.class);

    @Autowired
    UserEventRepository userEventRepository;

    @JmsListener(destination = AmqConfiguration.PUB_TOPIC, containerFactory = "jmsContainerFactory", id = "DB-Subcriber")
    public void processDatabaseSubscriber(UserEventMessage receivedUser, TextMessage message) {

        try {
            logger.debug(">>>>>>> Database Subscriber JMS message id: \n{}", message.getJMSMessageID());
            logger.debug(">>>>>>> UserEvent Object: \n{}", receivedUser);

            logger.info(">>>>>>> correlationId = {} messageId = {} \n{}", message.getJMSCorrelationID(),
                    message.getJMSMessageID(), receivedUser.toJson());
            // Try to Upsert User Info based on email address
            Iterator<UserEvent> userEventResult = userEventRepository
                    .findByEmail(receivedUser.getEmail())
                    .iterator();
            UserEvent userEvent = null;
            if (userEventResult.hasNext()) {
                userEvent = userEventResult.next();
                userEvent.setRating(receivedUser.getRating());
                userEvent.setActive(receivedUser.isActive());
                userEvent.setLastModified(Timestamp.from(Instant.now()));
            } else {
                userEvent = new UserEvent(receivedUser.getEmail(), receivedUser.getRating(), receivedUser.isActive());
            }
            userEventRepository.save(userEvent);

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

}
