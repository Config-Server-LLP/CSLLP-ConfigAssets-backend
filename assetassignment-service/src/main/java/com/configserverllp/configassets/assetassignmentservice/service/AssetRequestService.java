package com.configserverllp.configassets.assetassignmentservice.service;

import com.configserverllp.configassets.assetassignmentservice.dto.AssetRequestDTO;

import java.util.List;
import java.util.Optional;

public interface AssetRequestService {
    AssetRequestDTO createRequest(AssetRequestDTO requestDTO);
    Optional<AssetRequestDTO> getRequestById(Long id);
    List<AssetRequestDTO> getRequestsByUserId(Long userId);
    List<AssetRequestDTO> getPendingRequests();
    AssetRequestDTO approveRequest(Long id, String approvedBy);
    AssetRequestDTO rejectRequest(Long id, String rejectedBy, String reason);
    List<AssetRequestDTO> getRequestsByStatus(String status);
}
