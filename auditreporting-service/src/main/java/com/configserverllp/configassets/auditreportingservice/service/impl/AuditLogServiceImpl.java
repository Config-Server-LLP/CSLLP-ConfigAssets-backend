package com.configserverllp.configassets.auditreportingservice.service.impl;

import com.configserverllp.configassets.auditreportingservice.dto.AuditLogDTO;
import com.configserverllp.configassets.auditreportingservice.entity.AuditLog;
import com.configserverllp.configassets.auditreportingservice.exception.ResourceNotFoundException;
import com.configserverllp.configassets.auditreportingservice.mapper.AuditLogMapper;
import com.configserverllp.configassets.auditreportingservice.repository.AuditLogRepository;
import com.configserverllp.configassets.auditreportingservice.service.AuditLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogServiceImpl.class);

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository, AuditLogMapper auditLogMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditLogMapper = auditLogMapper;
    }

    @Override
    public List<AuditLogDTO> getAllAuditLogs() {
        try {
            return auditLogRepository.findAll()
                    .stream()
                    .map(AuditLogMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all audit logs", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByEntity(String entityType, Long entityId) {
        try {
            return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId)
                    .stream()
                    .map(AuditLogMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching audit logs for entity {} with id {}", entityType, entityId, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByUser(String username) {
        try {
            return auditLogRepository.findByPerformedBy(username)
                    .stream()
                    .map(AuditLogMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching audit logs for user {}", username, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByAction(String action) {
        try {
            return auditLogRepository.findByAction(action)
                    .stream()
                    .map(AuditLogMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching audit logs for action {}", action, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByDateRange(LocalDateTime start, LocalDateTime end) {
        try {
            return auditLogRepository.findByTimestampBetween(start, end)
                    .stream()
                    .map(AuditLogMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching audit logs from {} to {}", start, end, e);
            return Collections.emptyList();
        }
    }
}
