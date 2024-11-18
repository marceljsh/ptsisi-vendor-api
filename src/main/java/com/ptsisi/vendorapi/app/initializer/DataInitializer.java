package com.ptsisi.vendorapi.app.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final JdbcTemplate template;

  @Override
  public void run(String... args) throws Exception {
    Resource resource = new ClassPathResource("sql/init.sql");
    ResourceDatabasePopulator dbPopulator = new ResourceDatabasePopulator(resource);
    dbPopulator.execute(Objects.requireNonNull(template.getDataSource()));
  }

}
