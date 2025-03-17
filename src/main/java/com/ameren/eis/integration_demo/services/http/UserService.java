package com.ameren.eis.integration_demo.services.http;

import com.ameren.eis.integration_demo.model.UserEventMessage;

public interface UserService {

    void createMessage(UserEventMessage userEvent);
    void createEvent(UserEventMessage userEvent);

}