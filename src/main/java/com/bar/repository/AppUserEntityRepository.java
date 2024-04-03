package com.bar.repository;

import com.bar.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserEntityRepository extends JpaRepository<AppUserEntity, Long> {
    Optional<AppUserEntity> findUserEntityByUsername(String username);
    boolean existsByUsername(String username);
}
