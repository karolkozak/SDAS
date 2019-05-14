package com.sdas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NodeEntity
public class Tag {
    @GraphId
    private Long id;
    private String tagName;
    private int numberOfTweets;
    @Relationship(type = "REFERENCE", direction = Relationship.UNDIRECTED)
    private List<TweetEntity> referencedTweets;

    public void setReferencedTweets(List<TweetEntity> tweets) {
        if (referencedTweets == null) {
            referencedTweets = new ArrayList<>();
        }
        referencedTweets.clear();
        referencedTweets.addAll(tweets);
    }
}
