package com.configserverllp.configassets.assetassignmentservice.dto;

import com.configserverllp.configassets.assetassignmentservice.entity.AssignmentStatus;
import java.time.LocalDateTime;

public class AssetAssignmentDTO {
    private Long id;
    private Long userId;
    private LocalDateTime assignedDate;
    private LocalDateTime returnDate;
    private AssignmentStatus status;
    private String assignedBy;
    private String notes;
    private Long assetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "AssetAssignmentDTO{" +
                "id=" + id +
                ", assetId=" + assetId +
                ", userId=" + userId +
                ", assignedDate=" + assignedDate +
                ", returnDate=" + returnDate +
                ", status=" + status +
                ", assignedBy='" + assignedBy + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
    // Getters & Setters
}
