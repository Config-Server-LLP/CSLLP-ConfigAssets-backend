package com.configserverllp.configassets.assetservice.repository;

import com.configserverllp.configassets.assetservice.entity.Asset;
import com.configserverllp.configassets.assetservice.entity.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByAssetId(Long assetId);

    List<Asset> findByCategory(String category);

    List<Asset> findByStatus(AssetStatus status);

    List<Asset> findByStatusAndCategory(AssetStatus status, String category);

    List<Asset> findByStatusAndNameContainingIgnoreCase(AssetStatus status, String query);

    List<Asset> findByNameContainingIgnoreCase(String query);

    List<Asset> findByStatusAndCategoryAndNameContainingIgnoreCase(AssetStatus status, String category, String query);

    List<Asset> findByStatus(AssetStatus... statuses);

    // Add missing methods
    // Search by name (case-insensitive) OR by exact assetId parsed separately in service
    List<Asset> findByNameContainingIgnoreCaseOrAssetId(String nameQuery, Long assetId);
    
    List<Asset> findByCategoryAndStatus(String category, AssetStatus status);

}
