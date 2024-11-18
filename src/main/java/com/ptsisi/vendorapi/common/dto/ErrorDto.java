package com.ptsisi.vendorapi.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

  private String message;

  public static ErrorDto of(String message) {
    return ErrorDto.builder().message(message).build();
  }

}
