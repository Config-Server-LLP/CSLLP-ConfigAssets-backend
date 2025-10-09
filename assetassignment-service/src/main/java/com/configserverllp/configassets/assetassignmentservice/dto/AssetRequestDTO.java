package com.configserverllp.configassets.assetassignmentservice.dto;

import com.configserverllp.configassets.assetassignmentservice.entity.RequestStatus;
import java.time.LocalDateTime;

public class AssetRequestDTO {
    private Long id;
    private Long requestedById;
    private Long assetId;
    private String requestType;
    private String category;
    private String reason;
    private String priority;
    private RequestStatus status;
    private String approvedBy;
    private LocalDateTime approvedDate;
    private String rejectionReason;

    @Override
    public String toString() {
        return "AssetRequestDTO{" +
                "id=" + id +
                ", requestedById=" + requestedById +
                ", assetId=" + assetId +
                ", requestType='" + requestType + '\'' +
                ", category='" + category + '\'' +
                ", reason='" + reason + '\'' +
                ", priority='" + priority + '\'' +
                ", status=" + status +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedDate=" + approvedDate +
                ", rejectionReason='" + rejectionReason + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestedById() {
        return requestedById;
    }

    public void setRequestedById(Long requestedById) {
        this.requestedById = requestedById;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
