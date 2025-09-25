package com.configserverllp.configassets.auditreportingservice.controller;

import com.configserverllp.configassets.auditreportingservice.dto.AuditLogDTO;
import com.configserverllp.configassets.auditreportingservice.service.AuditLogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAllAuditLogs() {
        return ResponseEntity.ok(auditLogService.getAllAuditLogs());
    }

    @GetMapping("/entity/{type}/{id}")
    public ResponseEntity<List<AuditLogDTO>> getLogsByEntity(
            @PathVariable String type,
            @PathVariable Long id) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByEntity(type, id));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<AuditLogDTO>> getLogsByUser(@PathVariable String username) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByUser(username));
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLogDTO>> getLogsByAction(@PathVariable String action) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByAction(action));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<AuditLogDTO>> getLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByDateRange(start, end));
    }
}
