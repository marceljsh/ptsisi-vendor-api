package com.ptsisi.vendorapi.app.advice;

import com.ptsisi.vendorapi.common.dto.ApiResponse;
import com.ptsisi.vendorapi.common.dto.ErrorDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ptsisi.vendorapi")
public class PersistenceAdvice {

  private static final Logger log = LoggerFactory.getLogger(PersistenceAdvice.class);

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiResponse<ErrorDto>> handleEntityNotFoundException(EntityNotFoundException e) {
    log.error("Entity not found exception: {}", e.getMessage());

    ErrorDto error = ErrorDto.of(e.getMessage());
    ApiResponse<ErrorDto> body = ApiResponse.fail(error);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<ErrorDto>> handleValidationException(MethodArgumentNotValidException e) {
    log.error("Validation exception: {}", e.getMessage());

    String[] errors = e.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(error -> error.getField() + ":" + error.getDefaultMessage())
      .toArray(String[]::new);
    String errMsg = String.join(", ", errors);

    ErrorDto error = ErrorDto.of(errMsg);
    ApiResponse<ErrorDto> body = ApiResponse.fail(error);

    return ResponseEntity.badRequest().body(body);
  }

}
