package com.qoormthon.todool.domain.user.adapter.out.persistence;

import com.qoormthon.todool.domain.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    void deleteByUserId(String userId);
    boolean existsByUserId(String userId);
    UserEntity findByUserId(String userId);
}
