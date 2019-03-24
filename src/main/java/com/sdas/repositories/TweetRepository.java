package com.sdas.repositories;

import com.sdas.models.TweetEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends Neo4jRepository<TweetEntity, Long> {
    // This method causes dependency injection. Why?
//    TweetEntity findFirstByOrderByCreatedAtDesc();

    @Query("MATCH (t) RETURN t ORDER BY t.createdAt LIMIT 1")
    TweetEntity findLastByCreatedAt();
}
