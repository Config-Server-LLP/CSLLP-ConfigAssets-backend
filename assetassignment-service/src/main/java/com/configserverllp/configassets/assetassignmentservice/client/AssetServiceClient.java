package com.configserverllp.configassets.assetassignmentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "asset-service")
public interface AssetServiceClient {

    @GetMapping("/api/assets/{id}")
    AssetDTO getAssetById(@PathVariable Long id);

    @GetMapping("/api/assets/asset-id/{assetId}")
    AssetDTO getAssetByAssetId(@PathVariable String assetId);

    @GetMapping("/api/assets")
    List<AssetDTO> getAllAssets();

    @GetMapping("/api/assets/category/{category}")
    List<AssetDTO> getAssetsByCategory(@PathVariable String category);

    @GetMapping("/api/assets/status/{status}")
    List<AssetDTO> getAssetsByStatus(@PathVariable String status);

    @GetMapping("/api/assets/available")
    List<AssetDTO> getAvailableAssets();

    @PutMapping("/api/assets/{id}")
    AssetDTO updateAsset(@PathVariable Long id, @RequestBody AssetDTO assetDTO);
}

// AssetDTO class for FeignClient communication
class AssetDTO {
    private Long id;
    private String assetId;
    private String name;
    private String category;
    private String subCategory;
    private String brand;
    private String model;
    private String serialNumber;
    private String description;
    private String status;
    private String purchasePrice;
    private String purchaseDate;
    private String warrantyExpiry;
    private String location;
    private String notes;

    // Constructors
    public AssetDTO() {}

    public AssetDTO(Long id, String assetId, String name, String category, String subCategory, 
                   String brand, String model, String serialNumber, String description, String status,
                   String purchasePrice, String purchaseDate, String warrantyExpiry, String location, String notes) {
        this.id = id;
        this.assetId = assetId;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.description = description;
        this.status = status;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.location = location;
        this.notes = notes;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAssetId() { return assetId; }
    public void setAssetId(String assetId) { this.assetId = assetId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(String purchasePrice) { this.purchasePrice = purchasePrice; }

    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getWarrantyExpiry() { return warrantyExpiry; }
    public void setWarrantyExpiry(String warrantyExpiry) { this.warrantyExpiry = warrantyExpiry; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
