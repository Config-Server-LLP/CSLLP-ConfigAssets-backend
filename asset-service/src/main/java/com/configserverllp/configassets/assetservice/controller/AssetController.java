package com.configserverllp.configassets.assetservice.controller;

import com.configserverllp.configassets.assetservice.dto.AssetDTO;
import com.configserverllp.configassets.assetservice.entity.AssetStatus;
import com.configserverllp.configassets.assetservice.exception.AssetNotFoundException;
import com.configserverllp.configassets.assetservice.exception.DuplicateAssetException;
import com.configserverllp.configassets.assetservice.exception.InvalidAssetDataException;
import com.configserverllp.configassets.assetservice.service.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private static final Logger logger = LoggerFactory.getLogger(AssetController.class);

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<AssetDTO> createAsset(@RequestBody AssetDTO assetDTO) {
        logger.info("Received request to create asset: {}", assetDTO.getAssetId());
        try {
            AssetDTO created = assetService.createAsset(assetDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (DuplicateAssetException e) {
            logger.error("Duplicate asset creation attempt: {}", e.getMessage());
            throw e;
        } catch (InvalidAssetDataException e) {
            logger.error("Invalid asset data: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while creating asset", e);
            throw e;
        }
    }

    // ================= READ =================
    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getAssetById(@PathVariable Long id) {
        logger.info("Fetching asset with ID: {}", id);
        return assetService.getAssetById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AssetNotFoundException("Asset not found with id: " + id));
    }

    @GetMapping
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        logger.info("Fetching all assets");
        List<AssetDTO> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<AssetDTO>> getAssetsByCategory(@PathVariable String category) {
        logger.info("Fetching assets by category: {}", category);
        List<AssetDTO> assets = assetService.getAssetsByCategory(category);
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AssetDTO>> getAssetsByStatus(@PathVariable AssetStatus status) {
        logger.info("Fetching assets by status: {}", status);
        List<AssetDTO> assets = assetService.getAssetsByStatus(status);
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/available")
    public ResponseEntity<List<AssetDTO>> getAvailableAssets() {
        logger.info("Fetching all available assets");
        List<AssetDTO> assets = assetService.getAvailableAssets();
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AssetDTO>> searchAssets(@RequestParam("q") String query) {
        logger.info("Searching assets with query: {}", query);
        List<AssetDTO> assets = assetService.searchAssets(query);
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AssetDTO>> filterAssets(@RequestParam(value = "category", required = false) String category,
                                                       @RequestParam(value = "status", required = false) AssetStatus status) {
        logger.info("Filtering assets by category: {}, status: {}", category, status);
        List<AssetDTO> assets = assetService.filterAssets(category, status);
        return ResponseEntity.ok(assets);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> updateAsset(@PathVariable Long id, @RequestBody AssetDTO assetDTO) {
        logger.info("Updating asset with ID: {}", id);
        try {
            AssetDTO updated = assetService.updateAsset(id, assetDTO);
            return ResponseEntity.ok(updated);
        } catch (AssetNotFoundException e) {
            logger.error("Asset not found while updating: {}", e.getMessage());
            throw e;
        } catch (InvalidAssetDataException e) {
            logger.error("Invalid asset data while updating: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while updating asset", e);
            throw e;
        }
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        logger.info("Deleting asset with ID: {}", id);
        try {
            assetService.deleteAsset(id);
            return ResponseEntity.noContent().build();
        } catch (AssetNotFoundException e) {
            logger.error("Asset not found while deleting: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while deleting asset", e);
            throw e;
        }
    }
}
