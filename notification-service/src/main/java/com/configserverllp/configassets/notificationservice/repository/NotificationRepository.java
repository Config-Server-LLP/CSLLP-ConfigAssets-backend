package com.configserverllp.configassets.notificationservice.repository;

import com.configserverllp.configassets.notificationservice.entity.Notification;
import com.configserverllp.configassets.notificationservice.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Get notifications by recipient
    List<Notification> findByRecipient(String recipient);

    // Get notifications by status
    List<Notification> findByStatus(NotificationStatus status);

    // Custom query to get all templates (type = "TEMPLATE")
    @Query("SELECT n FROM Notification n WHERE n.type = 'TEMPLATE'")
    List<Notification> findAllTemplates();
}
