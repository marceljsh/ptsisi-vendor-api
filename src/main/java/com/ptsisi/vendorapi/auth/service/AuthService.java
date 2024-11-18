package com.ptsisi.vendorapi.auth.service;

import com.ptsisi.vendorapi.auth.dto.LoginRequest;
import com.ptsisi.vendorapi.auth.dto.RegisterRequest;
import com.ptsisi.vendorapi.user.model.User;

public interface AuthService {

  User register(RegisterRequest request);
  String authenticate(LoginRequest request);
  void logout(String token);

}
