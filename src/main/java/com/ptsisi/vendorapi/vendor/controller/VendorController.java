package com.ptsisi.vendorapi.vendor.controller;

import com.ptsisi.vendorapi.common.dto.ApiResponse;
import com.ptsisi.vendorapi.common.dto.PagedResponse;
import com.ptsisi.vendorapi.common.util.Constants;
import com.ptsisi.vendorapi.vendor.dto.VendorAddRequest;
import com.ptsisi.vendorapi.vendor.dto.VendorSearchRequest;
import com.ptsisi.vendorapi.vendor.dto.VendorUpdateRequest;
import com.ptsisi.vendorapi.vendor.model.Vendor;
import com.ptsisi.vendorapi.vendor.service.VendorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vendors")
@Validated
@RequiredArgsConstructor
public class VendorController {

  private static final Logger log = LoggerFactory.getLogger(VendorController.class);

  private final VendorService vendorService;

  @PostMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ApiResponse<Vendor>> createVendor(@RequestBody VendorAddRequest request) {
    log.info("Creating a new vendor with name: '{}' and location: '{}'", request.getName(), request.getLocation());

    Vendor newVendor = Vendor.builder()
        .name(request.getName())
        .location(request.getLocation())
        .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(vendorService.createVendor(newVendor)));
  }

  @GetMapping(
    value = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ApiResponse<Vendor>> getVendor(@PathVariable UUID id) {
    log.info("Fetching vendor with id: '{}'", id);

    var result = vendorService.getVendorById(id);
    if (result.isEmpty()) {
      throw new EntityNotFoundException(Constants.ERR_VENDOR_NOT_FOUND);
    }

    return ResponseEntity.ok(ApiResponse.success(result.get()));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<PagedResponse<Vendor>>> getVendors(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "location", required = false) String location,
      @RequestParam(value = "active-only", defaultValue = "true") boolean activeOnly,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    log.info("Fetching vendors with page: '{}' and size: '{}'", page, size);

    VendorSearchRequest request = VendorSearchRequest.of(name, location, activeOnly, page, size);
    var result = vendorService.getVendorList(request);
    var body = PagedResponse.from(result);

    return ResponseEntity.ok(ApiResponse.success(body));
  }

  @PutMapping(
    value = "/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ApiResponse<Vendor>> updateVendor(@PathVariable UUID id, @RequestBody VendorUpdateRequest request) {
    log.info("Updating vendor with id: '{}'", id);

    var result = vendorService.getVendorById(id);
    if (result.isEmpty()) {
      throw new EntityNotFoundException(Constants.ERR_VENDOR_NOT_FOUND);
    }

    return ResponseEntity.ok(ApiResponse.success(vendorService.updateVendor(id, request)));
  }

  @DeleteMapping(
    value = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ApiResponse<Object>> deleteVendor(@PathVariable UUID id) {
    log.info("Deleting vendor with id: '{}'", id);

    if (!vendorService.existsById(id)) {
      throw new EntityNotFoundException(Constants.ERR_VENDOR_NOT_FOUND);
    }

    Map<String, UUID> res = Map.of("id", vendorService.deleteVendor(id));
    return ResponseEntity.ok(ApiResponse.success(res));
  }

}
