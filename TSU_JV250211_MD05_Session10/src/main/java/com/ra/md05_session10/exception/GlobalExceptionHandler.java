package com.ra.md05_session10.exception;

import com.ra.md05_session10.model.dto.response.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomHandlerException.class)
    public ResponseEntity<String> handleCustomHandlerException(CustomHandlerException ex, HttpStatus  status){
        return new ResponseEntity<>(ex.getMessage(), status);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleHttpServerErrorException(HttpServerErrorException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponse<List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getDefaultMessage()).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(DataResponse.<List<String>>builder()
                        .status(400)
                        .message("Validation errors occurred.")
                        .data(errorMessages)
                        .build());
    }
}

