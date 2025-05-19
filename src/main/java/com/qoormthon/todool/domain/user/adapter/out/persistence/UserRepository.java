package com.qoormthon.todool.domain.user.adapter.out.persistence;

import com.qoormthon.todool.domain.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByStdNo(String StdNo);
    void deleteByStdNo(String StdNo);
    boolean existsByUserId(String userId);
    UserEntity findByUserId(String userId);
}
