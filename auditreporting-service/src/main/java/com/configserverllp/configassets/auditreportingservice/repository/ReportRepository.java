package com.configserverllp.configassets.auditreportingservice.repository;

import com.configserverllp.configassets.auditreportingservice.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByReportType(String type);

    List<Report> findByGeneratedBy(String username);
}
