package com.ptsisi.vendorapi.user.service;

import com.ptsisi.vendorapi.common.util.Constants;
import com.ptsisi.vendorapi.user.model.User;
import com.ptsisi.vendorapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepo;

  @Override
  public User register(User user) {
    return userRepo.save(user);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepo.existsByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepo.findByEmail(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException(Constants.ERR_USER_NOT_FOUND);
    }
    return user.get();
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepo.findByEmail(email);
  }

}
