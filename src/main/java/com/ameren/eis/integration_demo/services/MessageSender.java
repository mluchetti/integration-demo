package com.ameren.eis.integration_demo.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import com.ameren.eis.integration_demo.config.AmqConfiguration;
import com.ameren.eis.integration_demo.model.UserEventMessage;

@Service
public class MessageSender {
    private static Logger log = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(UserEventMessage myMessage) {
        log.debug("sending with convertAndSend() to queue <{}>", AmqConfiguration.USER_QUEUE);

        MessagePostProcessor mpp = message -> {
            message.setJMSCorrelationID(UUID.randomUUID().toString());
            message.setDoubleProperty("RatingFilter", myMessage.getRating());
            message.setBooleanProperty("ActiveFilter", myMessage.isActive());
            return message;
            };
        
        jmsTemplate.convertAndSend(AmqConfiguration.USER_QUEUE, myMessage, mpp);
    }

    public void sendTopic(UserEventMessage myMessage) {
        log.debug("sending with convertAndSend() to topic <{}>", AmqConfiguration.PUB_TOPIC);
        MessagePostProcessor mpp = message -> {
            message.setJMSCorrelationID(UUID.randomUUID().toString());
            message.setDoubleProperty("RatingFilter", myMessage.getRating());
            message.setBooleanProperty("ActiveFilter", myMessage.isActive());
            return message;
            };

        jmsTemplate.convertAndSend(AmqConfiguration.PUB_TOPIC, myMessage, mpp);
    }
}
