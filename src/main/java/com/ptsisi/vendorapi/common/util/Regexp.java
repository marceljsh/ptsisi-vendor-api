package com.ptsisi.vendorapi.common.util;

public class Regexp {

  private Regexp() {}

  public static final String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
  public static final String RAW_PASSWORD = "^[a-zA-Z0-9!@#$%^&*()_\\-+=\\[\\]{}|;:'\",.<>/?\\\\]*$";

}
