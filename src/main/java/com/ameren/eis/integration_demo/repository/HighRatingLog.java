package com.ameren.eis.integration_demo.repository;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
@Table(name="HighRatingLog")
public class HighRatingLog {

    public HighRatingLog(String email, double rating, boolean active, String messageId, String correlationId) {
        this.email = email;
        this.rating = rating;
        this.active = active;
        this.messageId = messageId;
        this.correlationId = correlationId;
    }

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
    @Column(name = "messageId", updatable = true, nullable = false)    
    private String messageId;
    @Column(name = "correlationId", updatable = true, nullable = false)    
    private String correlationId;

}
