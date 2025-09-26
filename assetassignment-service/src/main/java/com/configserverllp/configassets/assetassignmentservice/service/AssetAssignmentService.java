package com.configserverllp.configassets.assetassignmentservice.service;

import com.configserverllp.configassets.assetassignmentservice.dto.AssetAssignmentDTO;

import java.util.List;
import java.util.Optional;

public interface AssetAssignmentService {
    AssetAssignmentDTO createAssignment(AssetAssignmentDTO assignmentDTO);
    Optional<AssetAssignmentDTO> getAssignmentById(Long id);
    List<AssetAssignmentDTO> getAssignmentsByUserId(Long userId);
    List<AssetAssignmentDTO> getAssignmentsByAssetId(Long assetId);
    AssetAssignmentDTO returnAssignment(Long id); 
    List<AssetAssignmentDTO> getOverdueAssignments();
}
