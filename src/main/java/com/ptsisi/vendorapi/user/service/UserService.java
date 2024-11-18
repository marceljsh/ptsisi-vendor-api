package com.ptsisi.vendorapi.user.service;

import com.ptsisi.vendorapi.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

  User register(User user);
  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);

}
