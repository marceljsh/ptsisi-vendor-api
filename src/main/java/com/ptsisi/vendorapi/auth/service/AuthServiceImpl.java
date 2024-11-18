package com.ptsisi.vendorapi.auth.service;

import com.ptsisi.vendorapi.auth.dto.LoginRequest;
import com.ptsisi.vendorapi.auth.dto.RegisterRequest;
import com.ptsisi.vendorapi.common.util.Constants;
import com.ptsisi.vendorapi.security.service.JwtService;
import com.ptsisi.vendorapi.security.service.TokenBlacklistService;
import com.ptsisi.vendorapi.user.model.User;
import com.ptsisi.vendorapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
  private static final String PASSWORD_NOT_MATCH = "password not match";

  private final UserService userService;
  private final JwtService jwtService;
  private final TokenBlacklistService tokenService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authManager;

  @Override
  @Transactional
  public User register(RegisterRequest request) {
    log.debug("Registering user with email: {}", request.getEmail());

    if (userService.existsByEmail(request.getEmail())) {
      log.debug("User with email {} already exists", request.getEmail());
      throw new DataIntegrityViolationException(Constants.ERR_USER_EXISTS);
    }

    User newUser = User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();
    return userService.register(newUser);
  }

  @Override
  @Transactional
  public String authenticate(LoginRequest request) {
    log.debug("Authenticating user with email: {}", request.getEmail());

    User user = findUserOrThrow(request.getEmail());
    validatePassword(request.getPassword(), user.getPassword());

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword());
    authManager.authenticate(authenticationToken);

    return jwtService.generateToken(user);
  }

  @Override
  public void logout(String token) {
    tokenService.blacklistToken(token);
  }

  private User findUserOrThrow(String email) {
    return userService.findByEmail(email)
        .orElseThrow(() -> new BadCredentialsException(Constants.ERR_USER_NOT_FOUND));
  }

  private void validatePassword(String rawPassword, String encodedPassword) {
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      log.debug(PASSWORD_NOT_MATCH);
      throw new BadCredentialsException(PASSWORD_NOT_MATCH);
    }
  }

}
