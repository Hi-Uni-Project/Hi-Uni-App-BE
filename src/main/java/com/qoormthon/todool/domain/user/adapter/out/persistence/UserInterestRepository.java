package com.qoormthon.todool.domain.user.adapter.out.persistence;

import com.qoormthon.todool.domain.user.domain.entity.UserInterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInterestRepository extends JpaRepository<UserInterestEntity, Long> {
    List<UserInterestEntity> findAllByUserId(String userId);
}
