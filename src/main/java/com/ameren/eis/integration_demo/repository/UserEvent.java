package com.ameren.eis.integration_demo.repository;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="UserEvent")
public class UserEvent {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = true, nullable = false)    
    private Long id;
    @Column(name = "email", updatable = true, nullable = false)    
    private String email;
    @Column(name = "rating", updatable = true, nullable = false)    
    private double rating;
    @Column(name = "active", updatable = true, nullable = false)    
    private boolean active;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private Timestamp dateCreated;
    @UpdateTimestamp
    @Column 
    @JsonProperty(access = Access.READ_ONLY)
    private Timestamp lastModified;

    private static String ACTIVE = "Active";
    private static String INACTIVE = "InActive";

    public UserEvent(String email, double rating, boolean active) {
        this.email = email;
        this.rating = rating;
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public double getRating() {
        return rating;
    }

    public boolean isActive() {
        return active;
    }

    @JsonProperty(access = Access.READ_ONLY)
    public String getStatus() {
        return active ? ACTIVE : INACTIVE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{")
        .append("email='").append(email).append("\'")
        .append(", rating=").append(rating)
        .append(", status=").append(getStatus())
        .append('}');

        return sb.toString();
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

}
