package com.configserverllp.configassets.assetassignmentservice.repository;

import com.configserverllp.configassets.assetassignmentservice.entity.AssetRequest;
import com.configserverllp.configassets.assetassignmentservice.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRequestRepository extends JpaRepository<AssetRequest, Long> {
    List<AssetRequest> findByRequestedByUserId(Long userId);
    List<AssetRequest> findByStatus(RequestStatus status);
}
