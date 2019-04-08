package com.sdas.models;

import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@NodeEntity
public class UserProfile {
    @GraphId
    private Long id;
    private Long vendorUserId;
    private String screenName;
    private String name;
    private String lang;
    private int followersCount;
}
