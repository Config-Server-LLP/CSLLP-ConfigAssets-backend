package com.configserverllp.configassets.notificationservice.controller;

import com.configserverllp.configassets.notificationservice.dto.NotificationDTO;
import com.configserverllp.configassets.notificationservice.entity.NotificationStatus;
import com.configserverllp.configassets.notificationservice.exception.NotificationNotFoundException;
import com.configserverllp.configassets.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // ================= HEALTH CHECK =================
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Notification service is running!");
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        logger.info("Creating notification for recipient: {}", notificationDTO.getRecipient());
        try {
            NotificationDTO savedNotification = notificationService.createNotification(notificationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNotification);
        } catch (Exception e) {
            logger.error("Error creating notification: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ================= READ =================
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        logger.info("Fetching notification with ID: {}", id);
        NotificationDTO notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUser(@PathVariable String username) {
        logger.info("Fetching notifications for user: {}", username);
        List<NotificationDTO> notifications = notificationService.getNotificationsByUser(username);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByStatus(@PathVariable NotificationStatus status) {
        logger.info("Fetching notifications by status: {}", status);
        List<NotificationDTO> notifications = notificationService.getNotificationsByStatus(status);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications() {
        logger.info("Fetching all unread notifications");
        List<NotificationDTO> notifications = notificationService.getUnreadNotifications();
        return ResponseEntity.ok(notifications);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}/mark-read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long id) {
        logger.info("Marking notification as read, ID: {}", id);
        try {
            NotificationDTO updatedNotification = notificationService.markAsRead(id);
            return ResponseEntity.ok(updatedNotification);
        } catch (NotificationNotFoundException e) {
            logger.error("Notification not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while marking notification as read", e);
            throw e;
        }
    }

    // ================= TEMPLATE MANAGEMENT =================
    @PostMapping("/templates")
    public ResponseEntity<NotificationDTO> createTemplate(@RequestBody NotificationDTO templateDTO) {
        logger.info("Creating notification template: {}", templateDTO.getSubject());
        try {
            NotificationDTO savedTemplate = notificationService.createTemplate(templateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTemplate);
        } catch (Exception e) {
            logger.error("Error creating notification template: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/templates")
    public ResponseEntity<List<NotificationDTO>> getAllTemplates() {
        logger.info("Fetching all notification templates");
        List<NotificationDTO> templates = notificationService.getAllTemplates();
        return ResponseEntity.ok(templates);
    }

    @PutMapping("/templates/{id}")
    public ResponseEntity<NotificationDTO> updateTemplate(@PathVariable Long id, @RequestBody NotificationDTO templateDTO) {
        logger.info("Updating template ID: {}", id);
        try {
            NotificationDTO updatedTemplate = notificationService.updateTemplate(id, templateDTO);
            return ResponseEntity.ok(updatedTemplate);
        } catch (NotificationNotFoundException e) {
            logger.error("Template not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while updating template", e);
            throw e;
        }
    }
}
