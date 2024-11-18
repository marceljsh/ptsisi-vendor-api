package com.ptsisi.vendorapi.security.service;

import com.ptsisi.vendorapi.security.dao.TokenBlacklistDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

  private final TokenBlacklistDao tokenDao;

  @Override
  public boolean isBlacklisted(String token) {
    return tokenDao.isTokenBlacklisted(token);
  }

  @Override
  public void blacklistToken(String token) {
    if (!isBlacklisted(token)) {
      tokenDao.blacklist(token);
    }
  }

}
