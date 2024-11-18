package com.ptsisi.vendorapi.user.repository;

import com.ptsisi.vendorapi.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

  @Modifying
  @Transactional
  @Query("UPDATE User u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
  void softDeleteById(@Param("id") UUID id);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

}
