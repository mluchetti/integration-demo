package com.ameren.eis.integration_demo.services.http;

import org.springframework.stereotype.Service;

import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.services.MessageSender;

@Service
public class UserServiceImpl implements UserService {

    private MessageSender userPublisher;

    public UserServiceImpl(MessageSender publisher){
        this.userPublisher = publisher;
    }

    @Override
    public void createMessage(UserEventMessage userEvent) {
        userPublisher.send(userEvent);
    }

    @Override
    public void createEvent(UserEventMessage userEvent) {
        userPublisher.sendTopic(userEvent);
    }

}
