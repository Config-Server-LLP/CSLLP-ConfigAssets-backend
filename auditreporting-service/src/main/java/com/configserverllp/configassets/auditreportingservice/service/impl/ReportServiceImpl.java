package com.configserverllp.configassets.auditreportingservice.service.impl;

import com.configserverllp.configassets.auditreportingservice.dto.ReportDTO;
import com.configserverllp.configassets.auditreportingservice.entity.Report;
import com.configserverllp.configassets.auditreportingservice.exception.ResourceNotFoundException;
import com.configserverllp.configassets.auditreportingservice.mapper.ReportMapper;
import com.configserverllp.configassets.auditreportingservice.repository.ReportRepository;
import com.configserverllp.configassets.auditreportingservice.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public ReportServiceImpl(ReportRepository reportRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    @Override
    public ReportDTO generateReport(ReportDTO reportDTO) {
        try {
            Report report = reportMapper.toEntity(reportDTO);
            // Ensure required fields
            if (report.getGeneratedAt() == null) {
                report.setGeneratedAt(java.time.LocalDateTime.now());
            }
            Report saved = reportRepository.save(report);
            return reportMapper.toDTO(saved);
        } catch (Exception e) {
            logger.error("Error generating report", e);
            throw new RuntimeException("Failed to generate report");
        }
    }

    @Override
    public Optional<ReportDTO> getReportById(Long id) {
        return reportRepository.findById(id)
                .map(ReportMapper::toDTO)
                .or(() -> {
                    logger.warn("Report not found with id {}", id);
                    return Optional.empty();
                });
    }

    @Override
    public List<ReportDTO> getReportsByType(String type) {
        try {
            return reportRepository.findByReportType(type)
                    .stream()
                    .map(ReportMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching reports by type {}", type, e);
            return List.of();
        }
    }

    @Override
    public List<ReportDTO> getReportsByUser(String username) {
        try {
            return reportRepository.findByGeneratedBy(username)
                    .stream()
                    .map(ReportMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching reports by user {}", username, e);
            return List.of();
        }
    }

    @Override
    public void deleteReport(Long id) {
        try {
            if (!reportRepository.existsById(id)) {
                logger.warn("Report not found with id {}", id);
                throw new ResourceNotFoundException("Report not found with id " + id);
            }
            reportRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting report with id {}", id, e);
            throw new RuntimeException("Failed to delete report with id " + id);
        }
    }
}
