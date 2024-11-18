package com.ptsisi.vendorapi.vendor.service;

import com.ptsisi.vendorapi.common.util.Constants;
import com.ptsisi.vendorapi.vendor.dto.VendorSearchRequest;
import com.ptsisi.vendorapi.vendor.dto.VendorUpdateRequest;
import com.ptsisi.vendorapi.vendor.model.Vendor;
import com.ptsisi.vendorapi.vendor.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

  private static final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

  private final VendorRepository vendorRepo;

  @Override
  public boolean existsById(UUID id) {
    return vendorRepo.existsById(id);
  }

  @Override
  public Vendor createVendor(Vendor vendor) {
    return vendorRepo.save(vendor);
  }

  @Override
  public Optional<Vendor> getVendorById(UUID id) {
    return vendorRepo.findById(id);
  }

  @Override
  public Page<Vendor> getVendorList(VendorSearchRequest request) {
    Specification<Vendor> spec = ((root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (Objects.nonNull(request.getName())) {
        predicates.add(cb.like(
          cb.lower(root.get("name")),
          "%" + request.getName().toLowerCase() + "%"));
      }

      if (Objects.nonNull(request.getLocation())) {
        predicates.add(cb.like(
          cb.lower(root.get("location")),
          "%" + request.getLocation().toLowerCase() + "%"));
      }

      if (request.isActiveOnly()) {
        predicates.add(cb.isNull(root.get("deletedAt")));
      }

      return query.where(predicates.toArray(new Predicate[] {})).getRestriction();
    });

    Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
    Page<Vendor> vendors = vendorRepo.findAll(spec, pageable);

    if (vendors.getTotalElements() == 0) {
      log.info("Vendor search returned no results");
      return new PageImpl<>(List.of(), pageable, 0);
    }

    return new PageImpl<>(vendors.getContent(), pageable, vendors.getTotalElements());
  }

  @Override
  public Vendor updateVendor(UUID id, VendorUpdateRequest request) {
    Vendor vendor = vendorRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Constants.ERR_VENDOR_NOT_FOUND));
    BeanUtils.copyProperties(request, vendor);

    return vendorRepo.save(vendor);
  }

  @Override
  public UUID deleteVendor(UUID id) {
    if (existsById(id)) {
      vendorRepo.softDeleteById(id);
      return id;
    }
    return null;
  }

}
