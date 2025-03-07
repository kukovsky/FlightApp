package org.flightapp.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"experienceUUID"})
@ToString(exclude = "user")
public class Experience {
    Integer experienceId;
    String experienceComment;
    String experienceUUID;
    Boolean done;
    User user;
}
