package com.ptsisi.vendorapi.auth.controller;

import com.ptsisi.vendorapi.auth.dto.AuthResponse;
import com.ptsisi.vendorapi.auth.dto.LoginRequest;
import com.ptsisi.vendorapi.auth.dto.RegisterRequest;
import com.ptsisi.vendorapi.auth.service.AuthService;
import com.ptsisi.vendorapi.common.dto.ApiResponse;
import com.ptsisi.vendorapi.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);

  private final AuthService authService;

  @PostMapping(
    value = "/sign-up",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ApiResponse<AuthResponse>> signUp(@RequestBody RegisterRequest request) {
    log.info("Registering a new user with email: '{}'", request.getEmail());

    User user = authService.register(request);
    AuthResponse response = AuthResponse.builder()
        .token(authService.authenticate(LoginRequest.builder()
            .email(user.getEmail())
            .password(request.getPassword())
            .build()))
        .build();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success(response));
  }

  @PostMapping(
    value = "/sign-in",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ApiResponse<AuthResponse>> signIn(@RequestBody LoginRequest request) {
    log.info("Authenticating user with email: '{}'", request.getEmail());

    AuthResponse response = AuthResponse
        .builder()
        .token(authService.authenticate(request))
        .build();

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @PostMapping(value = "/sign-out")
  public ResponseEntity<Void> signOut(HttpServletRequest request) {
    log.info("Logging out user");

    String token = request.getHeader("Authorization").substring(7);
    authService.logout(token);

    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    SecurityContextHolder.clearContext();

    return ResponseEntity.ok().build();
  }

}
