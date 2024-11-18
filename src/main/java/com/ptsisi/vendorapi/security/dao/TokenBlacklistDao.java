package com.ptsisi.vendorapi.security.dao;

import com.ptsisi.vendorapi.common.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TokenBlacklistDao {

  private final JdbcTemplate template;

  @Transactional(readOnly = true)
  public boolean isTokenBlacklisted(String token) {
    Integer count = template.queryForObject(Constants.QUERY_IS_TOKEN_BLACKLISTED,
        new Object[] { token }, Integer.class);
    return count != null && count > 0;
  }

  @Modifying
  @Transactional
  public void blacklist(String token) {
    template.update(Constants.QUERY_BLACKLIST_TOKEN, token);
  }

}
