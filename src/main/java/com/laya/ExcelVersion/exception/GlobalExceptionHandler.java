package com.laya.ExcelVersion.exception;

import com.alibaba.excel.exception.ExcelAnalysisException;
import com.laya.ExcelVersion.constants.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //From Vriddhi
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    //From Laya
    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<String> handleInvalidFileFormatException(InvalidFileFormatException ex, WebRequest request) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle EmptyFileException
    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<String> handleEmptyFileException(EmptyFileException ex, WebRequest request) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex, WebRequest request) {
        return new ResponseEntity<>("File processing error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExcelAnalysisException.class)
    public ResponseEntity<String> handleExcelAnalysisException(ExcelAnalysisException ex, WebRequest request) {
        return new ResponseEntity<>("Invalid Excel file format: " + ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CorruptedFileException.class)
    public ResponseEntity<String> handleCorruptedFileException(CorruptedFileException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    // Handle all other exceptions not specifically handled
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        log.info("An unexpected error occurred: " + ex.getMessage()+" "+ex.getClass().getName());
        return new ResponseEntity<>("Error: An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to access this resource.");
    }


}
