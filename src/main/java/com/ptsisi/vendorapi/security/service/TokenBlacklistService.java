package com.ptsisi.vendorapi.security.service;

public interface TokenBlacklistService {

  boolean isBlacklisted(String token);
  void blacklistToken(String token);

}
