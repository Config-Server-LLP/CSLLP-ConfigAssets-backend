package com.configserverllp.configassets.auditreportingservice.service;

import com.configserverllp.configassets.auditreportingservice.dto.ReportDTO;

import java.util.List;
import java.util.Optional;

public interface ReportService {

    ReportDTO generateReport(ReportDTO reportDTO);

    Optional<ReportDTO> getReportById(Long id);

    List<ReportDTO> getReportsByType(String type);

    List<ReportDTO> getReportsByUser(String username);

    void deleteReport(Long id);
}
