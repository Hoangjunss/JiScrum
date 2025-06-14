package com.baconbao.JiScrum.exception;

import com.baconbao.JiScrum.dto.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler that catches and formats errors across the application.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle general exceptions that are not explicitly handled.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleAllExceptions(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    /**
     * Handle resource not found exceptions.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Resource not found: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    /**
     * Handle bad request scenarios like validation failure.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIResponse<Void>> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        log.warn("Bad request: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    /**
     * Handle missing request parameters.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<APIResponse<Void>> handleMissingParams(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.warn("Missing request parameter: {}", ex.getParameterName());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    /**
     * Handle invalid type passed in path or query parameters.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());
        log.warn(message);
        return buildCustomErrorResponse(HttpStatus.BAD_REQUEST, message, ex, request);
    }

    /**
     * Handle bean validation errors (e.g., @Valid annotated DTOs).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        log.warn("Validation failed: {}", validationErrors);

        APIResponse<Void> response = new APIResponse<>(
                false,
                "Validation error",
                null,
                validationErrors,
                request.getRequestURI()
        );
        response.setTimestamp(Instant.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Build error response with extracted root cause and location.
     */
    private ResponseEntity<APIResponse<Void>> buildErrorResponse(HttpStatus status, Exception ex, HttpServletRequest request) {
        String rootCause = extractRootCause(ex);
        String errorLocation = extractErrorLocation(ex);

        return ResponseEntity.status(status).body(new APIResponse<>(
                false,
                ex.getMessage(),
                null,
                List.of(rootCause + " at " + errorLocation),
                request.getRequestURI()
        ));
    }

    /**
     * Custom error message for known client-side type mismatch errors.
     */
    private ResponseEntity<APIResponse<Void>> buildCustomErrorResponse(HttpStatus status, String customMessage, Exception ex, HttpServletRequest request) {
        String errorLocation = extractErrorLocation(ex);

        return ResponseEntity.status(status).body(new APIResponse<>(
                false,
                customMessage,
                null,
                List.of(customMessage + " at " + errorLocation),
                request.getRequestURI()
        ));
    }

    /**
     * Extracts the root cause message from nested exceptions.
     */
    private String extractRootCause(Throwable ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getClass().getSimpleName() + ": " + cause.getMessage();
    }

    /**
     * Retrieves the method and line number where the exception occurred.
     */
    private String extractErrorLocation(Throwable ex) {
        if (ex.getStackTrace().length > 0) {
            StackTraceElement el = ex.getStackTrace()[0];
            return el.getClassName() + "." + el.getMethodName() + "(" + el.getFileName() + ":" + el.getLineNumber() + ")";
        }
        return "Unknown location";
    }
}
