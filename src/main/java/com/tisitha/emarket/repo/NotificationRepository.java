package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Page<Notification> findByUserEmail(String email, Pageable pageable);

    Optional<Notification> findByIdAndUserEmail(UUID notificationId,String email);
}
