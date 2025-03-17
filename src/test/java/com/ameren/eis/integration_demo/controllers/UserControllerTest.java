package com.ameren.eis.integration_demo.controllers;

import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.services.http.UserServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserServiceImpl userService;

    @Test
    void whenValidMessagePayload_thenOkRequest() throws Exception {
        UserEventMessage uem = new UserEventMessage();
        doNothing().when(userService).createMessage(uem);

        mvc.perform( MockMvcRequestBuilders
	      .post("/api/v1/users/message")
          .content("{ \"email\": \"integration-1@mail.com\", \"rating\": \"4.5\", \"active\": true }".getBytes())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isAccepted());
    }

    @Test
    void whenValidEventPayload_thenOkRequest() throws Exception {
        UserEventMessage uem = new UserEventMessage();
        doNothing().when(userService).createEvent(uem);

        mvc.perform( MockMvcRequestBuilders
	      .post("/api/v1/users/event")
          .content("{ \"email\": \"integration-1@mail.com\", \"rating\": \"4.5\", \"active\": true }".getBytes())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isAccepted());
    }

    @ParameterizedTest (name = "when{0}_then400_BadRequest")
    @CsvSource(value = {
        "InvalidEmail | { \"email\": \"integrationmail.com\", \"rating\": \"8.5\", \"active\": true } | {\"email\":\"Please provide a valid email address\"}",
        "InvalidStringForRating | { \"email\": \"integrationmail.com\", \"rating\": \"g4.5\", \"active\": true } | JSON parse error: Cannot deserialize value of type `double` from String \"g4.5\": not a valid `double` value (as String to convert)",
        "NegativeValueForRating | { \"email\": \"integration@mail.com\", \"rating\": \"-1.5\", \"active\": true } | {\"rating\":\"Rating must be zero or greater\"}"
    }, delimiter = '|' )
    void whenInvalidPayload_thenBadRequest(String testName, String payload, String response) throws Exception {
        UserEventMessage uem = new UserEventMessage();
        doNothing().when(userService).createMessage(uem);

        mvc.perform( MockMvcRequestBuilders
	      .post("/api/v1/users/message")
          .content(payload.getBytes())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string(response))
        ;
    }

}
