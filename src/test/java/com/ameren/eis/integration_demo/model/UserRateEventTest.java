package com.ameren.eis.integration_demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserRateEventTest {

    @Test
    void createWithZeroArgConstructor(){
        //Act
        UserRateEvent urevent = new UserRateEvent();

        //Assert
        assertNotNull(urevent);
        
    }

    @Test
    void testToXml() {
        //Arrange
        UserRateEvent urevent = new UserRateEvent("unittest@mail.com", 0.0d);
        String content = Assertions.assertDoesNotThrow(() -> {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/Test-FILE-Sub.xml")));
        });

        //Assert
        assertEquals(content, Assertions.assertDoesNotThrow(urevent::toXml));

    }
}
