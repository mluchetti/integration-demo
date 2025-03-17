package com.ameren.eis.integration_demo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntegrationDemoApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

	@Test
	void testMain() {
		String[] args = new String[] {};

		// Mock SpringApplication.run to prevent actual application startup
		SpringApplication springApplicationMock = mock(SpringApplication.class);

		// Spy on SpringApplication to override the static run method
		try (var mockedStatic = mockStatic(SpringApplication.class)) {
			mockedStatic.when(() -> SpringApplication.run(IntegrationDemoApplication.class, args))
					.thenReturn(null);

			// Act
			IntegrationDemoApplication.main(args);

			// Assert
			mockedStatic.verify(() -> SpringApplication.run(IntegrationDemoApplication.class, args));

		}
	}
}
