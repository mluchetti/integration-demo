package com.ameren.eis.integration_demo.services;

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
public class MessageSubscriber {

    private static Logger logger = LoggerFactory.getLogger(MessageSubscriber.class);

    @Autowired
    UserEventRepository userEventRepository;

    @JmsListener(destination = AmqConfiguration.USER_QUEUE, containerFactory = "jmsContainerFactory", id = "P2P-Channel")
    public void processMessage(UserEventMessage receivedUser, TextMessage message) {

        logger.debug(">>>>>>> JMS message: \n{}", message);
        logger.debug(">>>>>>> UserEvent Object: \n{}", receivedUser);

        try {
            // P2P-Channel specific logic here
            UserEvent userEvent = new UserEvent(receivedUser.getEmail(), receivedUser.getRating(),
                    receivedUser.isActive());
            userEvent = userEventRepository.save(userEvent);
            
            logger.info(">>>>>>> correlationId = {} messageId = {} \n{}", 
                    message.getJMSCorrelationID(),
                    message.getJMSMessageID(), 
                    userEvent.toJson());

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

  

}
