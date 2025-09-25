package com.configserverllp.configassets.assetassignmentservice.mapper;

import com.configserverllp.configassets.assetassignmentservice.dto.AssetRequestDTO;
import com.configserverllp.configassets.assetassignmentservice.entity.AssetRequest;

public class AssetRequestMapper {

    public static AssetRequestDTO toDTO(AssetRequest request) {
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setId(request.getId());
        dto.setRequestedById(request.getRequestedByUserId());
        if (request.getAssetId() != null) {
            dto.setAssetId(request.getAssetId());
        }
        dto.setRequestType(request.getRequestType());
        dto.setCategory(request.getCategory());
        dto.setReason(request.getReason());
        dto.setPriority(request.getPriority());
        dto.setStatus(request.getStatus());
        dto.setApprovedBy(request.getApprovedBy());
        dto.setApprovedDate(request.getApprovedDate());
        dto.setRejectionReason(request.getRejectionReason());
        return dto;
    }

    public static AssetRequest toEntity(AssetRequestDTO dto) {
        AssetRequest request = new AssetRequest();
        request.setId(dto.getId());
        // Set Asset and User in service layer using Feign clients
        request.setRequestType(dto.getRequestType());
        request.setCategory(dto.getCategory());
        request.setReason(dto.getReason());
        request.setPriority(dto.getPriority());
        request.setStatus(dto.getStatus());
        request.setApprovedBy(dto.getApprovedBy());
        request.setApprovedDate(dto.getApprovedDate());
        request.setRejectionReason(dto.getRejectionReason());
        return request;
    }
}
