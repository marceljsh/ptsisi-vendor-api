package com.ptsisi.vendorapi.vendor.service;

import com.ptsisi.vendorapi.vendor.dto.VendorSearchRequest;
import com.ptsisi.vendorapi.vendor.dto.VendorUpdateRequest;
import com.ptsisi.vendorapi.vendor.model.Vendor;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface VendorService {

  boolean existsById(UUID id);
  Vendor createVendor(Vendor vendor);
  Optional<Vendor> getVendorById(UUID id);
  Page<Vendor> getVendorList(VendorSearchRequest request);
  Vendor updateVendor(UUID id, VendorUpdateRequest request);
  UUID deleteVendor(UUID id);

}
