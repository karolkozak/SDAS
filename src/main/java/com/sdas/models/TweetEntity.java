package com.sdas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NodeEntity
// TweetEntity, because Tweet class exist in spring social twitter
public class TweetEntity {
    @GraphId
    private Long id;
    private Long vendorId;
    private Date createdAt;
    private String text;
    @Relationship(type = "IN_REPLY_TO")
    private UserProfile inReplyToUser;
    @Relationship(type = "FROM")
    private UserProfile fromUser;
}
