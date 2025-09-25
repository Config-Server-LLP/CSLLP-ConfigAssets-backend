package com.configserverllp.configassets.assetservice.mapper;

import com.configserverllp.configassets.assetservice.dto.AssetDTO;
import com.configserverllp.configassets.assetservice.entity.Asset;

public class AssetMapper {

    // ===== Convert Entity to DTO =====
    public static AssetDTO toDTO(Asset asset) {
        if (asset == null) {
            return null;
        }

        AssetDTO dto = new AssetDTO();
        dto.setId(asset.getId());
        dto.setAssetId(asset.getAssetId());
        dto.setName(asset.getName());
        dto.setCategory(asset.getCategory());
        dto.setSubCategory(asset.getSubCategory());
        dto.setBrand(asset.getBrand());
        dto.setModel(asset.getModel());
        dto.setSerialNumber(asset.getSerialNumber());
        dto.setDescription(asset.getDescription());
        dto.setStatus(asset.getStatus());
        dto.setPurchasePrice(asset.getPurchasePrice());
        dto.setPurchaseDate(asset.getPurchaseDate());
        dto.setWarrantyExpiry(asset.getWarrantyExpiry());
        dto.setLocation(asset.getLocation());
        dto.setNotes(asset.getNotes());
        dto.setCreatedAt(asset.getCreatedAt());
        dto.setUpdatedAt(asset.getUpdatedAt());
        dto.setCreatedBy(asset.getCreatedBy());
        dto.setUpdatedBy(asset.getUpdatedBy());

        return dto;
    }

    // ===== Convert DTO to Entity =====
    public static Asset toEntity(AssetDTO dto) {
        if (dto == null) {
            return null;
        }

        Asset asset = new Asset();
        asset.setId(dto.getId());
        asset.setAssetId(dto.getAssetId());
        asset.setName(dto.getName());
        asset.setCategory(dto.getCategory());
        asset.setSubCategory(dto.getSubCategory());
        asset.setBrand(dto.getBrand());
        asset.setModel(dto.getModel());
        asset.setSerialNumber(dto.getSerialNumber());
        asset.setDescription(dto.getDescription());
        asset.setStatus(dto.getStatus());
        asset.setPurchasePrice(dto.getPurchasePrice());
        asset.setPurchaseDate(dto.getPurchaseDate());
        asset.setWarrantyExpiry(dto.getWarrantyExpiry());
        asset.setLocation(dto.getLocation());
        asset.setNotes(dto.getNotes());
        asset.setCreatedAt(dto.getCreatedAt());
        asset.setUpdatedAt(dto.getUpdatedAt());
        asset.setCreatedBy(dto.getCreatedBy());
        asset.setUpdatedBy(dto.getUpdatedBy());

        return asset;
    }
}
