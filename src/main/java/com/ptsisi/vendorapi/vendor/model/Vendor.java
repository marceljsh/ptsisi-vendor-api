package com.ptsisi.vendorapi.vendor.model;

import com.ptsisi.vendorapi.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "tbl_vendor")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Vendor extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String location;

}
