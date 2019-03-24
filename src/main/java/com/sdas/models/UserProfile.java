package com.sdas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NodeEntity
public class UserProfile {
    @GraphId
    private Long id;
    private Long vendorUserId;
    private String screenName;
    private String name;
}
