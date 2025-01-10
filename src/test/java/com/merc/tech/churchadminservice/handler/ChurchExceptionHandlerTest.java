package com.merc.tech.churchadminservice.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChurchExceptionHandlerTest {

    private ChurchExceptionHandler churchExceptionHandler;

    @BeforeEach
    void setUp() {
        churchExceptionHandler = new ChurchExceptionHandler();
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequestAndErrors() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        FieldError fieldError = new FieldError("church", "name", "Name is mandatory");
        BindException bindException = new BindException(new Object(), "church");
        bindException.addError(fieldError);
        when(ex.getBindingResult()).thenReturn(bindException);

        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("name", "Name is mandatory");

        Map<String, String> actualErrors = churchExceptionHandler.handleValidationExceptions(ex);

        assertEquals(expectedErrors, actualErrors);
    }

    @Test
    void handleNoResourceFoundException_ShouldReturnNotFoundAndMessage() {
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET,"Church not found!");

        String expectedMessage = "Church not found!";
        String actualMessage = churchExceptionHandler.handleValidationExceptions(ex);

        assertEquals(expectedMessage, actualMessage);
    }
}