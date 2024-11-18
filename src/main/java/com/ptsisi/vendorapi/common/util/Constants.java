package com.ptsisi.vendorapi.common.util;

public class Constants {

  private Constants() {
  }

  public static final String QUERY_IS_TOKEN_BLACKLISTED = """
         SELECT COUNT(*)
         FROM tbl_token_blacklist
         WHERE token = ?
      """;
  public static final String QUERY_BLACKLIST_TOKEN = """
         INSERT INTO tbl_token_blacklist (token)
         VALUES (?)
      """;

  public static final String ERR_DEFAULT = "something went wrong";
  public static final String ERR_AUTH_DEFAULT = "authentication failed";
  public static final String ERR_BAD_CREDENTIALS = "username or password is incorrect";
  public static final String ERR_USER_NOT_FOUND = "user not found";
  public static final String ERR_USER_EXISTS = "user already exists";
  public static final String ERR_VENDOR_NOT_FOUND = "vendor not found";

}
