package com.ameren.eis.integration_demo.services;

import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ameren.eis.integration_demo.config.AmqConfiguration;
import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.model.UserRateEvent;

import jakarta.jms.TextMessage;

@Service
public class MessageFileSubscriber {

    private static Logger logger = LoggerFactory.getLogger(MessageFileSubscriber.class);

    @JmsListener(destination = AmqConfiguration.PUB_TOPIC, selector = "ActiveFilter = true", containerFactory = "jmsContainerFactory", id = "FILE-Subscriber")
    public void processFileSubscriber(UserEventMessage receivedUser, TextMessage message) {
        FileWriter myWriter = null;
        try {
            logger.debug(">>>>>>> File Subscriber JMS message id: {}", message.getJMSMessageID());
            logger.debug(">>>>>>> UserEvent Object: \n{}", receivedUser);

            // Write to XML file
            UserRateEvent rateEvent = new UserRateEvent(receivedUser.getEmail(), receivedUser.getRating());
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmssX").format(Timestamp.from(Instant.now()));

            myWriter = new FileWriter("FILE-Sub-".concat(filename).concat(".xml"));
            myWriter.write(rateEvent.toXml());
            myWriter.close();

            logger.info(">>>>>>> correlationId = {} messageId = {} \n{}", message.getJMSCorrelationID(),
                    message.getJMSMessageID(), receivedUser.toJson());

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

}
