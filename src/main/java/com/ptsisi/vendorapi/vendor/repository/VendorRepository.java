package com.ptsisi.vendorapi.vendor.repository;

import com.ptsisi.vendorapi.vendor.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface VendorRepository extends JpaRepository<Vendor, UUID>, JpaSpecificationExecutor<Vendor> {

  @Modifying
  @Transactional
  @Query("UPDATE Vendor v SET v.deletedAt = CURRENT_TIMESTAMP WHERE v.id = :id")
  void softDeleteById(UUID id);

}
