package com.configserverllp.configassets.auditreportingservice.controller;

import com.configserverllp.configassets.auditreportingservice.dto.ReportDTO;
import com.configserverllp.configassets.auditreportingservice.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ReportDTO> generateReport(@RequestBody ReportDTO reportDTO) {
        return ResponseEntity.ok(reportService.generateReport(reportDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ReportDTO>> getReportsByType(@PathVariable String type) {
        return ResponseEntity.ok(reportService.getReportsByType(type));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ReportDTO>> getReportsByUser(@PathVariable String username) {
        return ResponseEntity.ok(reportService.getReportsByUser(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
