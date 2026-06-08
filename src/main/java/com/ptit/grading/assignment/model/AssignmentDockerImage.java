package com.ptit.grading.assignment.model;

import com.ptit.grading.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "assignment_docker_images")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDockerImage extends BaseEntity {

    @Column(nullable = false)
    private UUID assignmentId;

    @Column(nullable = false)
    private UUID dockerImageBaseId;
}
