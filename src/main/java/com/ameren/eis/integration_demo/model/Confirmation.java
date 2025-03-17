package com.ameren.eis.integration_demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Confirmation {

    private int ackNumber;
    private String verificationComment;

    public Confirmation(int ackNumber, String verificationComment) {
        this.ackNumber = ackNumber;
        this.verificationComment = verificationComment;
    }

    public int getAckNumber() {
        return ackNumber;
    }

    public String getVerificationComment() {
        return verificationComment;
    }

    @Override
    public String toString() {
        return String.format("Confirmation{ackNumber='%d', verificationComment=%s}",ackNumber, verificationComment);
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

}
