package com.configserverllp.configassets.auditreportingservice.mapper;

import com.configserverllp.configassets.auditreportingservice.dto.ReportDTO;
import com.configserverllp.configassets.auditreportingservice.entity.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public static ReportDTO toDTO(Report report) {
        if (report == null) return null;
        ReportDTO dto = new ReportDTO();
        dto.setId(report.getId());
        dto.setReportType(report.getReportType());
        dto.setGeneratedBy(report.getGeneratedBy());
        dto.setGeneratedAt(report.getGeneratedAt());
        dto.setReportData(report.getReportData());
        dto.setFilters(report.getFilters());
        dto.setFileName(report.getFileName());
        return dto;
    }

    public static Report toEntity(ReportDTO dto) {
        if (dto == null) return null;
        Report entity = new Report();
        entity.setId(dto.getId());
        entity.setReportType(dto.getReportType());
        entity.setGeneratedBy(dto.getGeneratedBy());
        entity.setGeneratedAt(dto.getGeneratedAt());
        entity.setReportData(dto.getReportData());
        entity.setFilters(dto.getFilters());
        entity.setFileName(dto.getFileName());
        return entity;
    }
}
