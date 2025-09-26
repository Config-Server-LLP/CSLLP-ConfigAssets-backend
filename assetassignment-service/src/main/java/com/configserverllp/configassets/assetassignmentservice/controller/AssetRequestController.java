package com.configserverllp.configassets.assetassignmentservice.controller;

import com.configserverllp.configassets.assetassignmentservice.dto.AssetRequestDTO;
import com.configserverllp.configassets.assetassignmentservice.exception.RequestNotFoundException;
import com.configserverllp.configassets.assetassignmentservice.service.AssetRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class AssetRequestController {

    private static final Logger logger = LoggerFactory.getLogger(AssetRequestController.class);
    private final AssetRequestService requestService;

    public AssetRequestController(AssetRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<AssetRequestDTO> createRequest(@RequestBody AssetRequestDTO requestDTO) {
        logger.info("Creating new request for userId: {}", requestDTO.getRequestedById());
        try {
            AssetRequestDTO saved = requestService.createRequest(requestDTO);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            logger.error("Error creating request", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetRequestDTO> getRequestById(@PathVariable Long id) {
        logger.info("Fetching request with id: {}", id);
        return requestService.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RequestNotFoundException("Request not found with id: " + id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssetRequestDTO>> getRequestsByUser(@PathVariable Long userId) {
        logger.info("Fetching requests for userId: {}", userId);
        List<AssetRequestDTO> requests = requestService.getRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<AssetRequestDTO>> getPendingRequests() {
        logger.info("Fetching pending requests");
        List<AssetRequestDTO> pending = requestService.getPendingRequests();
        return ResponseEntity.ok(pending);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<AssetRequestDTO> approveRequest(@PathVariable Long id,
                                                         @RequestParam String approvedBy) {
        logger.info("Approving request with id: {}", id);
        AssetRequestDTO updated = requestService.approveRequest(id, approvedBy);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<AssetRequestDTO> rejectRequest(@PathVariable Long id,
                                                        @RequestParam String rejectedBy,
                                                        @RequestParam String reason) {
        logger.info("Rejecting request with id: {}", id);
        AssetRequestDTO updated = requestService.rejectRequest(id, rejectedBy, reason);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AssetRequestDTO>> getRequestsByStatus(@PathVariable String status) {
        logger.info("Fetching requests with status: {}", status);
        List<AssetRequestDTO> requests = requestService.getRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }
}
