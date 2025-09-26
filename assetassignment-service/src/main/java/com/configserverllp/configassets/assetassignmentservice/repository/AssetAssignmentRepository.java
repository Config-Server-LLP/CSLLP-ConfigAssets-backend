package com.configserverllp.configassets.assetassignmentservice.repository;

import com.configserverllp.configassets.assetassignmentservice.entity.AssetAssignment;
import com.configserverllp.configassets.assetassignmentservice.entity.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetAssignmentRepository extends JpaRepository<AssetAssignment, Long> {
    List<AssetAssignment> findByUserId(Long userId);
    List<AssetAssignment> findByAssetId(Long assetId);
    List<AssetAssignment> findByStatus(AssignmentStatus status);
}
