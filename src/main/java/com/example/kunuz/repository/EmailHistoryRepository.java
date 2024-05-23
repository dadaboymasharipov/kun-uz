package com.example.kunuz.repository;

import com.example.kunuz.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity, String> {
}
