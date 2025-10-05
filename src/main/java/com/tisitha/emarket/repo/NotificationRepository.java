package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Notification;
import com.tisitha.emarket.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Page<Notification> findByUserEmail(String email, Pageable pageable);

    Optional<Notification> findByIdAndUserEmail(UUID notificationId,String email);

    int countBySeenAndUserEmail(boolean b, String userEmail);

    @Modifying
    @Query("UPDATE Notification n SET n.seen = true WHERE n.seen = false AND n.user.email = :userEmail")
    void markUnseenAsSeenOfUserEmail(@Param("userEmail") String userEmail);

}
