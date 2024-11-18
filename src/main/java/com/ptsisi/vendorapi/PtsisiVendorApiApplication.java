package com.ptsisi.vendorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:${user.dir}/.env")
public class PtsisiVendorApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(PtsisiVendorApiApplication.class, args);
  }

}
