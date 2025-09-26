package com.configserverllp.configassets.auditreportingservice.mapper;

import com.configserverllp.configassets.auditreportingservice.dto.AuditLogDTO;
import com.configserverllp.configassets.auditreportingservice.entity.AuditLog;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public static AuditLogDTO toDTO(AuditLog auditLog) {
        if (auditLog == null) return null;
        AuditLogDTO dto = new AuditLogDTO();
        dto.setId(auditLog.getId());
        dto.setEntityType(auditLog.getEntityType());
        dto.setEntityId(auditLog.getEntityId());
        dto.setAction(auditLog.getAction());
        dto.setPerformedBy(auditLog.getPerformedBy());
        dto.setOldValues(auditLog.getOldValues());
        dto.setNewValues(auditLog.getNewValues());
        dto.setTimestamp(auditLog.getTimestamp());
        dto.setIpAddress(auditLog.getIpAddress());
        dto.setUserAgent(auditLog.getUserAgent());
        dto.setDetails(auditLog.getDetails());
        return dto;
    }

    public static AuditLog toEntity(AuditLogDTO dto) {
        if (dto == null) return null;
        AuditLog entity = new AuditLog();
        entity.setId(dto.getId());
        entity.setEntityType(dto.getEntityType());
        entity.setEntityId(dto.getEntityId());
        entity.setAction(dto.getAction());
        entity.setPerformedBy(dto.getPerformedBy());
        entity.setOldValues(dto.getOldValues());
        entity.setNewValues(dto.getNewValues());
        entity.setTimestamp(dto.getTimestamp());
        entity.setIpAddress(dto.getIpAddress());
        entity.setUserAgent(dto.getUserAgent());
        entity.setDetails(dto.getDetails());
        return entity;
    }
}
