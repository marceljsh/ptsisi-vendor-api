package com.ptsisi.vendorapi.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

  private String token;

}
