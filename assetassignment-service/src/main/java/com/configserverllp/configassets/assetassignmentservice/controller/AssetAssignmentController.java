package com.configserverllp.configassets.assetassignmentservice.controller;

import com.configserverllp.configassets.assetassignmentservice.dto.AssetAssignmentDTO;
import com.configserverllp.configassets.assetassignmentservice.exception.AssignmentNotFoundException;
import com.configserverllp.configassets.assetassignmentservice.service.AssetAssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssetAssignmentController {

    private static final Logger logger = LoggerFactory.getLogger(AssetAssignmentController.class);
    private final AssetAssignmentService assignmentService;

    public AssetAssignmentController(AssetAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<AssetAssignmentDTO> createAssignment(@RequestBody AssetAssignmentDTO assignmentDTO) {
        logger.info("Creating new assignment for userId: {}", assignmentDTO.getUserId());
        try {
            AssetAssignmentDTO saved = assignmentService.createAssignment(assignmentDTO);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            logger.error("Error creating assignment", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetAssignmentDTO> getAssignmentById(@PathVariable Long id) {
        logger.info("Fetching assignment with id: {}", id);
        return assignmentService.getAssignmentById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment not found with id: " + id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssetAssignmentDTO>> getAssignmentsByUser(@PathVariable Long userId) {
        logger.info("Fetching assignments for userId: {}", userId);
        List<AssetAssignmentDTO> assignments = assignmentService.getAssignmentsByUserId(userId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<AssetAssignmentDTO>> getAssignmentsByAsset(@PathVariable Long assetId) {
        logger.info("Fetching assignments for assetId: {}", assetId);
        List<AssetAssignmentDTO> assignments = assignmentService.getAssignmentsByAssetId(assetId);
        return ResponseEntity.ok(assignments);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<AssetAssignmentDTO> returnAssignment(@PathVariable Long id) {
        logger.info("Returning assignment with id: {}", id);
        try {
            AssetAssignmentDTO updated = assignmentService.returnAssignment(id);
            return ResponseEntity.ok(updated);
        } catch (AssignmentNotFoundException e) {
            logger.error("Assignment not found while returning", e);
            throw e;
        }
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<AssetAssignmentDTO>> getOverdueAssignments() {
        logger.info("Fetching overdue assignments");
        List<AssetAssignmentDTO> overdue = assignmentService.getOverdueAssignments();
        return ResponseEntity.ok(overdue);
    }
}
