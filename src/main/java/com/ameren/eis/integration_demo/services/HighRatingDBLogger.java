package com.ameren.eis.integration_demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ameren.eis.integration_demo.config.AmqConfiguration;
import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.repository.HighRatingLog;
import com.ameren.eis.integration_demo.repository.HighRatingLogRepository;

import jakarta.jms.TextMessage;

@Service
public class HighRatingDBLogger {

    private static Logger logger = LoggerFactory.getLogger(HighRatingDBLogger.class);

    @Autowired
    HighRatingLogRepository highRatingLogRepository;

    @JmsListener(destination = AmqConfiguration.PUB_TOPIC, selector = "RatingFilter >= 5", containerFactory = "jmsContainerFactory", id = "DBLog-Subcriber")
    public void processHighRatingSubscriber(UserEventMessage receivedUser, TextMessage message) {

        try {
            logger.debug(">>>>>>> High Rating Subscriber JMS message id: \n{}", message.getJMSMessageID());
            logger.debug(">>>>>>> UserEvent Object: \n{}", receivedUser);

            logger.info(">>>>>>> correlationId = {} messageId = {} \n{}", message.getJMSCorrelationID(),
                    message.getJMSMessageID(), receivedUser.toJson());
            
            HighRatingLog uEventLog = new HighRatingLog(receivedUser.getEmail()
                , receivedUser.getRating(), receivedUser.isActive()
                , message.getJMSMessageID(), message.getJMSCorrelationID());

            highRatingLogRepository.save(uEventLog);

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

}
