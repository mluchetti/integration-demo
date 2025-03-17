package com.ameren.eis.integration_demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserEventMessageTest {
    
    @Test
    void testConstructor_noParameters(){        
        UserEventMessage event = new UserEventMessage();
        assertEquals(null, event.getEmail());
        assertFalse(event.isActive());
        assertEquals(0d, event.getRating());
    }

    @Test
    void testConstructor_withParameters(){        
        UserEventMessage event = new UserEventMessage("user@me.com", 2d, false);
        assertEquals("user@me.com", event.getEmail());
        assertFalse(event.isActive());
        assertEquals(2d, event.getRating());
    }
    
    @Test
    void testToJson() {
        UserEventMessage event = new UserEventMessage("usereventmsg@me.com", 2d, true);
        String jsonString = Assertions.assertDoesNotThrow(event::toJson);
        assertNotNull(jsonString);
        String content = Assertions.assertDoesNotThrow(() -> {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/UserEventMessage.json")));
        });
        assertEquals(content, jsonString);
    }

    @Test
    void testToString() {
        UserEventMessage event = new UserEventMessage("user@me.com", 2d, false);
        assertTrue(event.toString().startsWith("User{email='user@me.com"));
    }
}
