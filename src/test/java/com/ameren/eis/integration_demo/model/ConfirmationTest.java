package com.ameren.eis.integration_demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfirmationTest {
    @Test
    void testConfirmationCreate() {
        Confirmation conf = new Confirmation(0, "confirmation");
        assertEquals(0, conf.getAckNumber());
        assertEquals("confirmation", conf.getVerificationComment());
    }

    @Test
    void testToJson() {
        Confirmation conf = new Confirmation(0, "confirmation");
        String jsonString = Assertions.assertDoesNotThrow(conf::toJson);
        assertNotNull(jsonString);
        String content = Assertions.assertDoesNotThrow(() -> {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/Confirmation.json")));
        });
        assertEquals(content, jsonString);
    }

    @Test
    void testToString() {
        Confirmation conf = new Confirmation(0, "confirmation");
        assertNotNull(conf.toString());
    }
}
