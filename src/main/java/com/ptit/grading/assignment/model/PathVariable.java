package com.ptit.grading.assignment.model;

import com.ptit.grading.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Entity
@Table(name = "path_variables")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PathVariable extends BaseEntity {

    @Column(nullable = false)
    private UUID scenarioId;

    @Column(nullable = false)
    private String name;

    @Column(name = "variable_order", nullable = false)
    private Integer order;

    private String type;
}
