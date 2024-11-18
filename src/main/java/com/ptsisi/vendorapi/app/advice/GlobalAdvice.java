package com.ptsisi.vendorapi.app.advice;

import com.ptsisi.vendorapi.common.dto.ApiResponse;
import com.ptsisi.vendorapi.common.dto.ErrorDto;
import com.ptsisi.vendorapi.common.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ptsisi.vendorapi")
public class GlobalAdvice {

  private static final Logger log = LoggerFactory.getLogger(GlobalAdvice.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<ErrorDto>> handleException(Exception e) {
    log.error("Unspecified exception: {} with class [{}]", e.getMessage(), e.getClass());

    ErrorDto error = ErrorDto.of(Constants.ERR_DEFAULT);
    ApiResponse<ErrorDto> body = ApiResponse.fail(error);

    return ResponseEntity.internalServerError().body(body);
  }

}
