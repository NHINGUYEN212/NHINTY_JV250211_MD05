package md05_session11.advice;

import md05_session11.exception.CustomException;
import md05_session11.exception.ForbiddenException;
import md05_session11.model.dto.response.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalException {

    private ResponseEntity<DataResponse<String>> buildErrorResponse(HttpStatus status, String message) {
        DataResponse<String> response = DataResponse.<String>builder()
                .status(status.value())
                .message(message)
                .data(null)
                .build();
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<DataResponse<String>> notSuchElement(NoSuchElementException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<DataResponse<String>> internalServerError(HttpServerErrorException.InternalServerError e) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<DataResponse<String>> forbidden(ForbiddenException e) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<DataResponse<String>> customException(CustomException e) {
        return buildErrorResponse(e.getStatus(), e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataResponse<String>> handleAllExceptions(Exception e) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        DataResponse<Map<String, String>> response = DataResponse.<Map<String, String>>builder()
                .status(400)
                .message("Validation errors occurred")
                .data(errors)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<DataResponse<Map<String, String>>> handleBindExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        DataResponse<Map<String, String>> response = DataResponse.<Map<String, String>>builder()
                .status(400)
                .message("Validation errors occurred")
                .data(errors)
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}