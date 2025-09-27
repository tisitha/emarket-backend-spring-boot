package com.tisitha.emarket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String message;

    private String attachedId;

    private NotificationType notificationType;

    private boolean seen;

    private LocalDateTime dateAndTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
