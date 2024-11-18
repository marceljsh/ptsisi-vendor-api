package com.ptsisi.vendorapi.app.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HomeController {

  private static final String DOCUMENTATION_URL = "https://github.com/marceljsh/ptsisi-vendor-api?tab=readme-ov-file#auth";

  /**
   * Redirects to the documentation.
   *
   * @param response the HTTP response
   * @throws IOException if an I/O error occurs
   */
  @GetMapping({ "/", "/api/v1", "/api/v1/docs" })
  @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
  public void root(HttpServletResponse response) throws IOException {
    response.sendRedirect(DOCUMENTATION_URL);
  }

}
