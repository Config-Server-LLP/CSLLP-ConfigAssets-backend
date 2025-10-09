package com.configserverllp.configassets.assetassignmentservice.service.impl;

import com.configserverllp.configassets.assetassignmentservice.dto.AssetAssignmentDTO;
import com.configserverllp.configassets.assetassignmentservice.entity.AssetAssignment;
import com.configserverllp.configassets.assetassignmentservice.entity.AssignmentStatus;
import com.configserverllp.configassets.assetassignmentservice.exception.AssignmentNotFoundException;
import com.configserverllp.configassets.assetassignmentservice.mapper.AssetAssignmentMapper;
import com.configserverllp.configassets.assetassignmentservice.repository.AssetAssignmentRepository;
import com.configserverllp.configassets.assetassignmentservice.service.AssetAssignmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssetAssignmentServiceImpl implements AssetAssignmentService {

    private final AssetAssignmentRepository assignmentRepository;

    public AssetAssignmentServiceImpl(AssetAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public AssetAssignmentDTO createAssignment(AssetAssignmentDTO assignmentDTO) {
        // Basic validation of required fields
        if (assignmentDTO.getAssetId() == null) {
            throw new IllegalArgumentException("Asset ID is required");
        }
        if (assignmentDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        // TODO: Fetch Asset and User via Feign clients
        AssetAssignment assignment = AssetAssignmentMapper.toEntity(assignmentDTO);
        assignment.setStatus(AssignmentStatus.ACTIVE);
        assignment.setAssignedDate(LocalDateTime.now());
        return AssetAssignmentMapper.toDTO(assignmentRepository.save(assignment));
    }

    @Override
    public Optional<AssetAssignmentDTO> getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .map(AssetAssignmentMapper::toDTO)
                .or(() -> { throw new AssignmentNotFoundException("Assignment not found with id: " + id); });
    }

    @Override
    public List<AssetAssignmentDTO> getAssignmentsByUserId(Long userId) {
        return assignmentRepository.findByUserId(userId)
                .stream()
                .map(AssetAssignmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssetAssignmentDTO> getAssignmentsByAssetId(Long assetId) {
        return assignmentRepository.findByAssetId(assetId)
                .stream()
                .map(AssetAssignmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AssetAssignmentDTO returnAssignment(Long id) {
        AssetAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found with id: " + id));
        assignment.setStatus(AssignmentStatus.RETURNED);
        assignment.setReturnDate(LocalDateTime.now());
        return AssetAssignmentMapper.toDTO(assignmentRepository.save(assignment));
    }

    @Override
    public List<AssetAssignmentDTO> getOverdueAssignments() {
        return assignmentRepository.findByStatus(AssignmentStatus.OVERDUE)
                .stream()
                .map(AssetAssignmentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
