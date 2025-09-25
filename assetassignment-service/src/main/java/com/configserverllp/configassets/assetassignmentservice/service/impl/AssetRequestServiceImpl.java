package com.configserverllp.configassets.assetassignmentservice.service.impl;

import com.configserverllp.configassets.assetassignmentservice.dto.AssetRequestDTO;
import com.configserverllp.configassets.assetassignmentservice.entity.AssetRequest;
import com.configserverllp.configassets.assetassignmentservice.entity.RequestStatus;
import com.configserverllp.configassets.assetassignmentservice.exception.AssetNotFoundException;
import com.configserverllp.configassets.assetassignmentservice.exception.InvalidRequestException;
import com.configserverllp.configassets.assetassignmentservice.exception.RequestNotFoundException;
import com.configserverllp.configassets.assetassignmentservice.exception.UserNotFoundException;
import com.configserverllp.configassets.assetassignmentservice.mapper.AssetRequestMapper;
import com.configserverllp.configassets.assetassignmentservice.repository.AssetRequestRepository;
import com.configserverllp.configassets.assetassignmentservice.service.AssetRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssetRequestServiceImpl implements AssetRequestService {

    private final AssetRequestRepository requestRepository;

    public AssetRequestServiceImpl(AssetRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public AssetRequestDTO createRequest(AssetRequestDTO requestDTO) {
        // TODO: Fetch User and optional Asset via Feign clients
        AssetRequest request = AssetRequestMapper.toEntity(requestDTO);
        request.setStatus(RequestStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());
        return AssetRequestMapper.toDTO(requestRepository.save(request));
    }

    @Override
    public Optional<AssetRequestDTO> getRequestById(Long id) {
        return requestRepository.findById(id)
                .map(AssetRequestMapper::toDTO)
                .or(() -> { throw new RequestNotFoundException("Request not found with id: " + id); });
    }

    @Override
    public List<AssetRequestDTO> getRequestsByUserId(Long userId) {
        return requestRepository.findByRequestedByUserId(userId)
                .stream()
                .map(AssetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssetRequestDTO> getPendingRequests() {
        return requestRepository.findByStatus(RequestStatus.PENDING)
                .stream()
                .map(AssetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AssetRequestDTO approveRequest(Long id, String approvedBy) {
        AssetRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Request not found with id: " + id));
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new InvalidRequestException("Only pending requests can be approved");
        }
        request.setStatus(RequestStatus.APPROVED);
        request.setApprovedBy(approvedBy);
        request.setApprovedDate(LocalDateTime.now());
        return AssetRequestMapper.toDTO(requestRepository.save(request));
    }

    @Override
    public AssetRequestDTO rejectRequest(Long id, String rejectedBy, String reason) {
        AssetRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Request not found with id: " + id));
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new InvalidRequestException("Only pending requests can be rejected");
        }
        request.setStatus(RequestStatus.REJECTED);
        request.setApprovedBy(rejectedBy);
        request.setRejectionReason(reason);
        request.setApprovedDate(LocalDateTime.now());
        return AssetRequestMapper.toDTO(requestRepository.save(request));
    }

    @Override
    public List<AssetRequestDTO> getRequestsByStatus(String status) {
        RequestStatus rs;
        try {
            rs = RequestStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid request status: " + status);
        }

        return requestRepository.findByStatus(rs)
                .stream()
                .map(AssetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }
}
