package com.sdas.repositories;

import com.sdas.models.UserProfile;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile, Long> {
    UserProfile findUserProfileByVendorUserId(Long vendorUserId);
}
