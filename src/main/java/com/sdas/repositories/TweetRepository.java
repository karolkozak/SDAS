package com.sdas.repositories;

import com.sdas.models.TweetEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends Neo4jRepository<TweetEntity, Long> {
    TweetEntity findTweetEntityByVendorId(Long vendorId);

    @Query("MATCH (t:TweetEntity) WHERE t.text is not null RETURN t ORDER BY t.createdAt desc LIMIT 1")
    TweetEntity findLastByCreatedAt();

    @Query("MATCH (t:TweetEntity) WHERE t.text is not null and {tag} IN t.tags RETURN t ORDER BY t.createdAt desc LIMIT 1")
    TweetEntity findTweetCustomPatryk(@Param("tag")String tag);
}
