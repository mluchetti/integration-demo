package com.ameren.eis.integration_demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ameren.eis.integration_demo.repository.UserEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;

class UserEventTest {

    @Test
    void testUserEventCreation() {
        UserEvent event = new UserEvent("user@me.com", 2d, false);
        assertEquals("user@me.com", event.getEmail());
        assertFalse(event.isActive());
        assertEquals(2d, event.getRating());
    }
    @Test
    void testUserEvent_toString() {
        UserEvent event = new UserEvent("user@me.com", 2d, false);
        assertTrue(event.toString().startsWith("User{email='"));
    }
    @Test
    void testUserEvent_toJson() {
        UserEvent event = new UserEvent("user@me.com", 5.4d, true);
        String jsonString = Assertions.assertDoesNotThrow(event::toJson);
        assertNotNull(jsonString);
        String content = Assertions.assertDoesNotThrow(() -> {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/UserEvent.json")));
        });
        ObjectMapper om = new ObjectMapper();
        UserEvent ue1 = new UserEvent();
        try {
            ue1 = om.readValue(jsonString, UserEvent.class);
            assertEquals(event.getEmail(), ue1.getEmail());
            assertNotNull(content);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e);
        }
    }
}
