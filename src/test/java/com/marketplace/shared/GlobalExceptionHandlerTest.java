package com.marketplace.shared;

import com.marketplace.domain.exception.BadResourceRequestException;
import com.marketplace.domain.exception.ConflictException;
import com.marketplace.domain.exception.NoSuchResourceFoundException;
import com.marketplace.domain.exception.UnprocessableEntityException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private HttpServletRequest request;

    private final String TEST_PATH = "/api/test/products";
    private final String TEST_MESSAGE = "Test error message";

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
        // NO configurar el mock aquí para evitar stubbings innecesarios
    }

    @Test
    void handleNotFound_ShouldReturn404WithCorrectBody() {
        // Arrange
        NoSuchResourceFoundException exception = new NoSuchResourceFoundException(TEST_MESSAGE);
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNotFound(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("NOT_FOUND", body.get("error"));
        assertEquals(TEST_MESSAGE, body.get("message"));
        assertEquals(TEST_PATH, body.get("path"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof Instant);

        verify(request, times(1)).getRequestURI();
    }

    @Test
    void handleBadRequest_ShouldReturn400WithCorrectBody() {
        // Arrange
        BadResourceRequestException exception = new BadResourceRequestException(TEST_MESSAGE);
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleBadRequest(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("BAD_REQUEST", body.get("error"));
        assertEquals(TEST_MESSAGE, body.get("message"));
        assertEquals(TEST_PATH, body.get("path"));
        assertNotNull(body.get("timestamp"));

        verify(request, times(1)).getRequestURI();
    }

    @Test
    void handleConflict_ShouldReturn409WithCorrectBody() {
        // Arrange
        ConflictException exception = new ConflictException(TEST_MESSAGE);
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleConflict(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(409, body.get("status"));
        assertEquals("CONFLICT", body.get("error"));
        assertEquals(TEST_MESSAGE, body.get("message"));
        assertEquals(TEST_PATH, body.get("path"));
        assertNotNull(body.get("timestamp"));

        verify(request, times(1)).getRequestURI();
    }

    @Test
    void handleUnprocessable_ShouldReturn422WithCorrectBody() {
        // Arrange
        UnprocessableEntityException exception = new UnprocessableEntityException(TEST_MESSAGE);
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleUnprocessable(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(422, body.get("status"));
        assertEquals("UNPROCESSABLE_ENTITY", body.get("error"));
        assertEquals(TEST_MESSAGE, body.get("message"));
        assertEquals(TEST_PATH, body.get("path"));
        assertNotNull(body.get("timestamp"));

        verify(request, times(1)).getRequestURI();
    }

    @Test
    void handleGeneric_WithRuntimeException_ShouldReturn500WithGenericMessage() {
        // Arrange
        RuntimeException exception = new RuntimeException("Detalle técnico no mostrado");
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGeneric(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("status"));
        assertEquals("INTERNAL_ERROR", body.get("error"));
        assertEquals("Unexpected internal error", body.get("message"));
        assertEquals(TEST_PATH, body.get("path"));
        assertNotNull(body.get("timestamp"));

        verify(request, times(1)).getRequestURI();
    }

    @Test
    void handleGeneric_WithNullPointerException_ShouldReturn500WithGenericMessage() {
        // Arrange
        NullPointerException exception = new NullPointerException();
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGeneric(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("status"));
        assertEquals("INTERNAL_ERROR", body.get("error"));
        assertEquals("Unexpected internal error", body.get("message"));
        assertEquals(TEST_PATH, body.get("path"));

        verify(request, times(1)).getRequestURI();
    }

    @Test
    void handleGeneric_WithIllegalArgumentException_ShouldReturn500WithGenericMessage() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Argumento inválido");
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGeneric(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("status"));
        assertEquals("INTERNAL_ERROR", body.get("error"));
        assertEquals("Unexpected internal error", body.get("message"));
        assertEquals(TEST_PATH, body.get("path"));

        verify(request, times(1)).getRequestURI();
    }

    @Test
    void handleNotFound_WithEmptyMessage_ShouldHandleGracefully() {
        // Arrange
        NoSuchResourceFoundException exception = new NoSuchResourceFoundException("");
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNotFound(exception, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("NOT_FOUND", body.get("error"));
        assertEquals("", body.get("message"));
        assertEquals(TEST_PATH, body.get("path"));
    }


    @Test
    void handleBadRequest_WithNullRequest_ShouldThrowException() {
        // Arrange
        BadResourceRequestException exception = new BadResourceRequestException(TEST_MESSAGE);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
                exceptionHandler.handleBadRequest(exception, null)
        );
    }

    @Test
    void build_WithAllParameters_ShouldCreateCorrectResponse() {
        // Arrange
        NoSuchResourceFoundException exception = new NoSuchResourceFoundException(TEST_MESSAGE);
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNotFound(exception, request);

        // Assert
        Map<String, Object> body = response.getBody();
        assertNotNull(body);

        assertTrue(body.containsKey("timestamp"));
        assertTrue(body.containsKey("status"));
        assertTrue(body.containsKey("error"));
        assertTrue(body.containsKey("message"));
        assertTrue(body.containsKey("path"));

        assertTrue(body.get("timestamp") instanceof Instant);
        assertTrue(body.get("status") instanceof Integer);
        assertTrue(body.get("error") instanceof String);
        assertTrue(body.get("path") instanceof String);
    }

    @Test
    void multipleExceptions_ShouldAllReturnConsistentFormat() {
        // Arrange
        NoSuchResourceFoundException notFoundEx = new NoSuchResourceFoundException(TEST_MESSAGE);
        BadResourceRequestException badRequestEx = new BadResourceRequestException(TEST_MESSAGE);
        ConflictException conflictEx = new ConflictException(TEST_MESSAGE);
        UnprocessableEntityException unprocessableEx = new UnprocessableEntityException(TEST_MESSAGE);
        Exception genericEx = new Exception(TEST_MESSAGE);

        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> notFoundResponse = exceptionHandler.handleNotFound(notFoundEx, request);
        ResponseEntity<Map<String, Object>> badRequestResponse = exceptionHandler.handleBadRequest(badRequestEx, request);
        ResponseEntity<Map<String, Object>> conflictResponse = exceptionHandler.handleConflict(conflictEx, request);
        ResponseEntity<Map<String, Object>> unprocessableResponse = exceptionHandler.handleUnprocessable(unprocessableEx, request);
        ResponseEntity<Map<String, Object>> genericResponse = exceptionHandler.handleGeneric(genericEx, request);

        // Assert
        assertAll(
                () -> assertResponseFormat(notFoundResponse, 404, "NOT_FOUND", TEST_MESSAGE),
                () -> assertResponseFormat(badRequestResponse, 400, "BAD_REQUEST", TEST_MESSAGE),
                () -> assertResponseFormat(conflictResponse, 409, "CONFLICT", TEST_MESSAGE),
                () -> assertResponseFormat(unprocessableResponse, 422, "UNPROCESSABLE_ENTITY", TEST_MESSAGE),
                () -> assertResponseFormat(genericResponse, 500, "INTERNAL_ERROR", "Unexpected internal error")
        );
    }

    private void assertResponseFormat(ResponseEntity<Map<String, Object>> response,
                                      int expectedStatus,
                                      String expectedError,
                                      String expectedMessage) {
        assertNotNull(response);
        assertEquals(expectedStatus, response.getStatusCode().value());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(expectedStatus, body.get("status"));
        assertEquals(expectedError, body.get("error"));
        assertEquals(expectedMessage, body.get("message"));
        assertEquals(TEST_PATH, body.get("path"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void handleGeneric_WithExceptionAndNullRequest_ShouldThrowException() {
        // Arrange
        Exception exception = new Exception(TEST_MESSAGE);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
                exceptionHandler.handleGeneric(exception, null)
        );
    }

    @Test
    void handleNotFound_WithDifferentPaths_ShouldReflectPath() {
        // Arrange
        NoSuchResourceFoundException exception = new NoSuchResourceFoundException(TEST_MESSAGE);
        String differentPath = "/api/different/path";
        when(request.getRequestURI()).thenReturn(differentPath);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNotFound(exception, request);

        // Assert
        assertNotNull(response);
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(differentPath, body.get("path"));

        verify(request, times(1)).getRequestURI();
    }

    @Test
    void handleConflict_WithSpecialCharactersInMessage_ShouldPreserveMessage() {
        // Arrange
        String specialMessage = "Error: <tag> & special chars ' \" \\";
        ConflictException exception = new ConflictException(specialMessage);
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleConflict(exception, request);

        // Assert
        assertNotNull(response);
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(specialMessage, body.get("message"));
    }

    @Test
    void timestamp_ShouldBeRecent() {
        // Arrange
        NoSuchResourceFoundException exception = new NoSuchResourceFoundException(TEST_MESSAGE);
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNotFound(exception, request);

        // Assert
        Map<String, Object> body = response.getBody();
        assertNotNull(body);

        Instant timestamp = (Instant) body.get("timestamp");
        Instant now = Instant.now();

        long diffInSeconds = Math.abs(timestamp.getEpochSecond() - now.getEpochSecond());
        assertTrue(diffInSeconds < 2, "Timestamp debería ser reciente");
    }
}
