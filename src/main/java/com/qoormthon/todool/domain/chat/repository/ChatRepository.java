package com.qoormthon.todool.domain.chat.repository;

import com.qoormthon.todool.domain.chat.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, String> {
}
