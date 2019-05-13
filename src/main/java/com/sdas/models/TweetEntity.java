package com.sdas.models;

import com.sun.istack.internal.Nullable;
import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@NodeEntity
// TweetEntity, because Tweet class exist in spring social twitter
public class TweetEntity {
    @GraphId
    private Long id;
    private Long vendorId;
    private Date createdAt;
    private String text;
    private Set<String> tags;
    @Relationship(type = "IN_REPLY_TO_USER")
    @Nullable
    private UserProfile inReplyToUser;
    @Relationship(type = "IN_REPLY_TO_STATUS")
    @Nullable
    private TweetEntity inReplyToStatus;
    @Relationship(type = "FROM")
    private UserProfile fromUser;
}
