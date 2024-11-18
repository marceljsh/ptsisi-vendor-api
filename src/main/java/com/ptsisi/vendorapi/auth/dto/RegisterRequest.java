package com.ptsisi.vendorapi.auth.dto;

import com.ptsisi.vendorapi.common.util.Regexp;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

  @Size(min = 3, max = 255,
    message = "Email must be {min}-{max} characters long")
  @Pattern(regexp = Regexp.EMAIL,
    message = "Email must be a valid email address")
  private String email;

  @Size(min = 8, max = 255,
    message = "Password must be {min}-{max} characters long")
  @Pattern(regexp = Regexp.RAW_PASSWORD,
    message = "Password must contain only letters, numbers, and special characters")
  private String password;

}
