package com.configserverllp.configassets.assetservice.service;

import com.configserverllp.configassets.assetservice.dto.AssetDTO;
import com.configserverllp.configassets.assetservice.entity.AssetStatus;

import java.util.List;
import java.util.Optional;

public interface AssetService {

    // ===== Asset Management =====
    AssetDTO createAsset(AssetDTO assetDTO);

    AssetDTO updateAsset(Long id, AssetDTO assetDTO);

    void deleteAsset(Long id);

    Optional<AssetDTO> getAssetById(Long id);

    List<AssetDTO> getAllAssets();

    List<AssetDTO> getAssetsByCategory(String category);

    List<AssetDTO> getAssetsByStatus(AssetStatus status);

    List<AssetDTO> getAvailableAssets();

    // ===== Asset Search & Filter =====
    List<AssetDTO> searchAssets(String query);

    List<AssetDTO> filterAssets(String category, AssetStatus status);
}
