package com.configserverllp.configassets.assetassignmentservice.mapper;

import com.configserverllp.configassets.assetassignmentservice.dto.AssetAssignmentDTO;
import com.configserverllp.configassets.assetassignmentservice.entity.AssetAssignment;

public class AssetAssignmentMapper {

    public static AssetAssignmentDTO toDTO(AssetAssignment assignment) {
        AssetAssignmentDTO dto = new AssetAssignmentDTO();
        dto.setId(assignment.getId());
        dto.setAssetId(assignment.getAssetId());
        dto.setUserId(assignment.getUserId());
        dto.setAssignedDate(assignment.getAssignedDate());
        dto.setReturnDate(assignment.getReturnDate());
        dto.setStatus(assignment.getStatus());
        dto.setAssignedBy(assignment.getAssignedBy());
        dto.setNotes(assignment.getNotes());
        return dto;
    }

    public static AssetAssignment toEntity(AssetAssignmentDTO dto) {
        AssetAssignment assignment = new AssetAssignment();
        assignment.setId(dto.getId());
        // Set Asset and User objects in service layer using Feign clients
        assignment.setAssignedDate(dto.getAssignedDate());
        assignment.setReturnDate(dto.getReturnDate());
        assignment.setStatus(dto.getStatus());
        assignment.setAssignedBy(dto.getAssignedBy());
        assignment.setNotes(dto.getNotes());
        return assignment;
    }
}
