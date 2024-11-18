package com.ptsisi.vendorapi.common.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class PagedResponse<T> {

  private Iterable<T> content;
  private int currentPage;
  private int totalPages;
  private int size;
  private long totalElements;

  public static <T> PagedResponse<T> from(Page<T> page) {
    return PagedResponse.<T>builder()
        .content(page.getContent())
        .currentPage(page.getNumber())
        .totalPages(page.getTotalPages())
        .size(page.getSize())
        .totalElements(page.getTotalElements())
        .build();
  }

}
