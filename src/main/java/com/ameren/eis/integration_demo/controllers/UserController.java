package com.ameren.eis.integration_demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.ameren.eis.integration_demo.model.UserEventMessage;
import com.ameren.eis.integration_demo.services.http.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(
    path = "/api/v1/users",
    produces = { "application/json" },
    consumes = { "application/json" }
)
@Validated
public class UserController {

    UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/message", name = "Publish User Message")
    @ApiResponse(responseCode = "202")
    @ApiResponse(responseCode = "400")
    @Operation(description = "Publishes a message to the point to point channel (Queue with single consumer)")
    public ResponseEntity<UserEventMessage> publishMessage(@Valid @RequestBody UserEventMessage userEvent) {
        userService.createMessage(userEvent);
        return new ResponseEntity<>(userEvent, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/event", name = "Publish User Topic")
    @ApiResponse(responseCode = "202")
    @ApiResponse(responseCode = "400")
    @Operation(description = "Publishes an event to a pub-sub channel (Topic with multiple consumers)")
    public ResponseEntity<UserEventMessage> publishEvent(@Valid @RequestBody UserEventMessage userEvent) {
        userService.createEvent(userEvent);
        return new ResponseEntity<>(userEvent, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
