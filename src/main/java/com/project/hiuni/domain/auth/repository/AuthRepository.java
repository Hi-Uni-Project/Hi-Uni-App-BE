package com.project.hiuni.domain.auth.repository;

import com.project.hiuni.domain.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

  void deleteById(Long id);

}
