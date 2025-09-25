package com.configserverllp.configassets.notificationservice.service;

import com.configserverllp.configassets.notificationservice.dto.NotificationDTO;
import com.configserverllp.configassets.notificationservice.entity.NotificationStatus;

import java.util.List;

public interface NotificationService {

    NotificationDTO createNotification(NotificationDTO notificationDTO);

    NotificationDTO getNotificationById(Long id);

    List<NotificationDTO> getNotificationsByUser(String username);

    List<NotificationDTO> getNotificationsByStatus(NotificationStatus status);

    NotificationDTO markAsRead(Long id);

    List<NotificationDTO> getUnreadNotifications();

    // Template management
    NotificationDTO createTemplate(NotificationDTO templateDTO);

    List<NotificationDTO> getAllTemplates();

    NotificationDTO updateTemplate(Long id, NotificationDTO templateDTO);
}
