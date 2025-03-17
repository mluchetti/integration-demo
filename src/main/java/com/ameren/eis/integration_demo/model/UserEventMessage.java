package com.ameren.eis.integration_demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class UserEventMessage {
    
    @Schema(example = "integration-1@mail.com", format = "email", description = "User's email", nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Schema(example = "4.5", description = "User's rating", nullable = false, type = "number", format = "double")
    @PositiveOrZero(message = "Rating must be zero or greater")
    private double rating;

    private boolean active = false;

    public UserEventMessage() {
    }

    public UserEventMessage(String email, double rating, boolean active) {
        this.email = email;
        this.rating = rating;
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public Double getRating() {
        return rating;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{")
        .append("email='").append(email).append("\'")
        .append(", rating=").append(rating)
        .append(", active=").append(active)
        .append('}');

        return sb.toString();
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

}
