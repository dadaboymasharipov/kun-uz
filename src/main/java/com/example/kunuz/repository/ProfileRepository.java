package com.example.kunuz.repository;

import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {
    Boolean existsByEmailAndVisibleTrue(String email);

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = ?2 where id = ?1")
    int updateStatus(String userId, ProfileStatus profileStatus);

}
