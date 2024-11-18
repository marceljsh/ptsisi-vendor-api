package com.ptsisi.vendorapi.vendor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorSearchRequest {

  private String name;
  private String location;
  private boolean activeOnly;
  private int page;
  private int size;

  public static VendorSearchRequest of(String name, String location, boolean activeOnly, int page, int pageSize) {
    return VendorSearchRequest.builder()
        .name(name)
        .location(location)
        .activeOnly(activeOnly)
        .page(page)
        .size(pageSize)
        .build();
  }

}
