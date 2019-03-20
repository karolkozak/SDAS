package com.sdas.repositories;

import com.sdas.models.Tweet;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends Neo4jRepository<Tweet, Long> {
}
