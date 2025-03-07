package org.flightapp.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"experienceId"})
@ToString(exclude = {"user"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "travel_experience")
public class ExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    private Integer experienceId;

    @Column(name = "experience_UUID", nullable = false, length = 50, unique = true)
    private String experienceUUID;

    @Column(name = "experience_comment", nullable = false)
    private String experienceComment;

    @Column(name = "done")
    private Boolean done;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;
}
