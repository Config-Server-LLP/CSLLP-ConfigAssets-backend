package com.configserverllp.configassets.assetservice.service.impl;

import com.configserverllp.configassets.assetservice.dto.AssetDTO;
import com.configserverllp.configassets.assetservice.entity.Asset;
import com.configserverllp.configassets.assetservice.entity.AssetStatus;
import com.configserverllp.configassets.assetservice.exception.DuplicateAssetException;
import com.configserverllp.configassets.assetservice.exception.InvalidAssetDataException;
import com.configserverllp.configassets.assetservice.exception.AssetNotFoundException;
import com.configserverllp.configassets.assetservice.mapper.AssetMapper;
import com.configserverllp.configassets.assetservice.repository.AssetRepository;
import com.configserverllp.configassets.assetservice.service.AssetService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {

    private static final Logger logger = LoggerFactory.getLogger(AssetServiceImpl.class);

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    // ===== CREATE =====
    @Override
    public AssetDTO createAsset(AssetDTO assetDTO) {
        try {
            logger.info("Creating asset with ID: {}", assetDTO.getAssetId());

            Optional<Asset> existing = assetRepository.findByAssetId(assetDTO.getAssetId());
            if (existing.isPresent()) {
                throw new DuplicateAssetException("Asset with assetId already exists: " + assetDTO.getAssetId());
            }

            Asset asset = AssetMapper.toEntity(assetDTO);
            Asset saved = assetRepository.save(asset);
            return AssetMapper.toDTO(saved);
        } catch (DuplicateAssetException | InvalidAssetDataException e) {
            logger.error("Error creating asset: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while creating asset", e);
            throw new RuntimeException("Unexpected error while creating asset");
        }
    }

    // ===== UPDATE =====
    @Override
    public AssetDTO updateAsset(Long id, AssetDTO assetDTO) {
        try {
            Asset updated = assetRepository.findById(id)
                    .map(existing -> {
                        existing.setAssetId(assetDTO.getAssetId());
                        existing.setName(assetDTO.getName());
                        existing.setCategory(assetDTO.getCategory());
                        existing.setSubCategory(assetDTO.getSubCategory());
                        existing.setBrand(assetDTO.getBrand());
                        existing.setModel(assetDTO.getModel());
                        existing.setSerialNumber(assetDTO.getSerialNumber());
                        existing.setDescription(assetDTO.getDescription());
                        existing.setStatus(assetDTO.getStatus());
                        existing.setPurchasePrice(assetDTO.getPurchasePrice());
                        existing.setPurchaseDate(assetDTO.getPurchaseDate());
                        existing.setWarrantyExpiry(assetDTO.getWarrantyExpiry());
                        existing.setLocation(assetDTO.getLocation());
                        existing.setNotes(assetDTO.getNotes());
                        logger.info("Updated asset with ID: {}", id);
                        return assetRepository.save(existing);
                    }).orElseThrow(() -> new AssetNotFoundException("Asset not found with ID: " + id));

            return AssetMapper.toDTO(updated);
        } catch (AssetNotFoundException | InvalidAssetDataException e) {
            logger.error("Error updating asset: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while updating asset", e);
            throw new RuntimeException("Unexpected error while updating asset");
        }
    }

    // ===== DELETE =====
    @Override
    public void deleteAsset(Long id) {
        if (!assetRepository.existsById(id)) {
            throw new AssetNotFoundException("Asset not found with ID: " + id);
        }
        logger.info("Deleting asset with ID: {}", id);
        assetRepository.deleteById(id);
    }

    // ===== GET BY ID =====
    @Override
    public Optional<AssetDTO> getAssetById(Long id) {
        return assetRepository.findById(id).map(AssetMapper::toDTO);
    }

    // ===== GET ALL =====
    @Override
    public List<AssetDTO> getAllAssets() {
        return assetRepository.findAll()
                .stream()
                .map(AssetMapper::toDTO)
                .sorted((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()))
                .collect(Collectors.toList());
    }

    // ===== GET BY CATEGORY =====
    @Override
    public List<AssetDTO> getAssetsByCategory(String category) {
        return assetRepository.findByCategory(category)
                .stream()
                .map(AssetMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== GET BY STATUS =====
    @Override
    public List<AssetDTO> getAssetsByStatus(AssetStatus status) {
        return assetRepository.findByStatus(status)
                .stream()
                .map(AssetMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== GET AVAILABLE ASSETS =====
    @Override
    public List<AssetDTO> getAvailableAssets() {
        return assetRepository.findByStatus(AssetStatus.AVAILABLE)
                .stream()
                .map(AssetMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== SEARCH =====
    @Override
    public List<AssetDTO> searchAssets(String query) {
        return assetRepository.findByNameContainingIgnoreCaseOrAssetIdContainingIgnoreCase(query, query)
                .stream()
                .map(AssetMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== FILTER =====
    @Override
    public List<AssetDTO> filterAssets(String category, AssetStatus status) {
        return assetRepository.findByCategoryAndStatus(category, status)
                .stream()
                .map(AssetMapper::toDTO)
                .collect(Collectors.toList());
    }
}
