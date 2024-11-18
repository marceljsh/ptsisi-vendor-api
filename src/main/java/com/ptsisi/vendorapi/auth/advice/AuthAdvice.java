package com.ptsisi.vendorapi.auth.advice;

import com.ptsisi.vendorapi.common.dto.ApiResponse;
import com.ptsisi.vendorapi.common.dto.ErrorDto;
import com.ptsisi.vendorapi.common.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ptsisi.vendorapi.auth")
public class AuthAdvice {

  private static final Logger log = LoggerFactory.getLogger(AuthAdvice.class);


  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiResponse<ErrorDto>> handleBadCredentialsException(BadCredentialsException e) {
    log.error("Bad credentials exception: {}", e.getMessage());

    ErrorDto error = ErrorDto.of(Constants.ERR_BAD_CREDENTIALS);
    ApiResponse<ErrorDto> body = ApiResponse.fail(error);

    return ResponseEntity.badRequest().body(body);
  }

}
