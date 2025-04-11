package com.qoormthon.todool.domain.user.repository;

import com.qoormthon.todool.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserEmail(String email);
    void deleteByUserEmail(String email);
    boolean existsByUserEmail(String email);
}
