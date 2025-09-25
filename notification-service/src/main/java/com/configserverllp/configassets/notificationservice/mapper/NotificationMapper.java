package com.configserverllp.configassets.notificationservice.mapper;

import com.configserverllp.configassets.notificationservice.dto.NotificationDTO;
import com.configserverllp.configassets.notificationservice.entity.Notification;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {

    public static NotificationDTO toDTO(Notification notification) {
        if (notification == null) return null;

        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setRecipient(notification.getRecipient());
        dto.setType(notification.getType());
        dto.setSubject(notification.getSubject());
        dto.setMessage(notification.getMessage());
        dto.setStatus(notification.getStatus());
        dto.setScheduledAt(notification.getScheduledAt());
        dto.setSentAt(notification.getSentAt());
        dto.setErrorMessage(notification.getErrorMessage());
        dto.setRetryCount(notification.getRetryCount());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }

    public static Notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;

        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setRecipient(dto.getRecipient());
        notification.setType(dto.getType());
        notification.setSubject(dto.getSubject());
        notification.setMessage(dto.getMessage());
        notification.setStatus(dto.getStatus());
        notification.setScheduledAt(dto.getScheduledAt());
        notification.setSentAt(dto.getSentAt());
        notification.setErrorMessage(dto.getErrorMessage());
        notification.setRetryCount(dto.getRetryCount());
        notification.setCreatedAt(dto.getCreatedAt());
        return notification;
    }

    public static List<NotificationDTO> toDTOList(List<Notification> notifications) {
        return notifications.stream()
                .map(NotificationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
