package com.configserverllp.configassets.notificationservice.service.impl;

import com.configserverllp.configassets.notificationservice.dto.NotificationDTO;
import com.configserverllp.configassets.notificationservice.entity.Notification;
import com.configserverllp.configassets.notificationservice.entity.NotificationStatus;
import com.configserverllp.configassets.notificationservice.exception.ResourceNotFoundException;
import com.configserverllp.configassets.notificationservice.mapper.NotificationMapper;
import com.configserverllp.configassets.notificationservice.repository.NotificationRepository;
import com.configserverllp.configassets.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        try {
            Notification entity = NotificationMapper.toEntity(notificationDTO);
            entity.setCreatedAt(LocalDateTime.now());
            Notification saved = repository.save(entity);
            return NotificationMapper.toDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create notification", e);
        }
    }

    @Override
    public NotificationDTO getNotificationById(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return NotificationMapper.toDTO(notification);
    }

    @Override
    public List<NotificationDTO> getNotificationsByUser(String username) {
        return NotificationMapper.toDTOList(
                repository.findByRecipient(username)
        );
    }

    @Override
    public List<NotificationDTO> getNotificationsByStatus(NotificationStatus status) {
        return NotificationMapper.toDTOList(
                repository.findByStatus(status)
        );
    }

    @Override
    public NotificationDTO markAsRead(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notification.setStatus(NotificationStatus.SENT);
        Notification updated = repository.save(notification);
        return NotificationMapper.toDTO(updated);
    }

    @Override
    public List<NotificationDTO> getUnreadNotifications() {
        return NotificationMapper.toDTOList(
                repository.findByStatus(NotificationStatus.PENDING)
        );
    }

    // Template Management

    @Override
    public NotificationDTO createTemplate(NotificationDTO templateDTO) {
        try {
            Notification entity = NotificationMapper.toEntity(templateDTO);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setStatus(NotificationStatus.PENDING); // Template default
            Notification saved = repository.save(entity);
            return NotificationMapper.toDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create template", e);
        }
    }

    @Override
    public List<NotificationDTO> getAllTemplates() {
        return NotificationMapper.toDTOList(
                repository.findAllTemplates()
        );
    }

    @Override
    public NotificationDTO updateTemplate(Long id, NotificationDTO templateDTO) {
        Notification existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with id: " + id));
        existing.setSubject(templateDTO.getSubject());
        existing.setMessage(templateDTO.getMessage());
        existing.setType(templateDTO.getType());
        Notification updated = repository.save(existing);
        return NotificationMapper.toDTO(updated);
    }
}
