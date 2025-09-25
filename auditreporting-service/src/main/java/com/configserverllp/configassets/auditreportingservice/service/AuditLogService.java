package com.configserverllp.configassets.auditreportingservice.service;

import com.configserverllp.configassets.auditreportingservice.dto.AuditLogDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {

    List<AuditLogDTO> getAllAuditLogs();

    List<AuditLogDTO> getAuditLogsByEntity(String entityType, Long entityId);

    List<AuditLogDTO> getAuditLogsByUser(String username);

    List<AuditLogDTO> getAuditLogsByAction(String action);

    List<AuditLogDTO> getAuditLogsByDateRange(LocalDateTime start, LocalDateTime end);
}
