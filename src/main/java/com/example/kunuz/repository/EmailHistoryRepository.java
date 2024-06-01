package com.example.kunuz.repository;

import com.example.kunuz.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity, String> {

    List<EmailHistoryEntity> findAllByEmail(String email);

    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);

    Optional<EmailHistoryEntity> findByEmail(String email);
}
