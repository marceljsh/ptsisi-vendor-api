package com.ptsisi.vendorapi.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

  private boolean success;
  private T data;

  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
        .success(true)
        .data(data)
        .build();
  }

  public static <T> ApiResponse<T> fail(T data) {
    return ApiResponse.<T>builder()
        .success(false)
        .data(data)
        .build();
  }

}
