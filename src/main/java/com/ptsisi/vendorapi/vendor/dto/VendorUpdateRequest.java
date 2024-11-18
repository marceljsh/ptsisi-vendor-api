package com.ptsisi.vendorapi.vendor.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorUpdateRequest {

  @Size(
    min = 3,
    max = 255,
    message = "Name must be {min}-{max} characters long"
  )
  private String name;

  @Size(
    min = 3,
    max = 255,
    message = "Location must be {min}-{max} characters long"
  )
  private String location;

}
