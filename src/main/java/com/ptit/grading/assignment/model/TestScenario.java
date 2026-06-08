package com.ptit.grading.assignment.model;

import com.ptit.grading.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.util.UUID;

@Entity
@Table(name = "test_scenarios")
@SQLDelete(sql = "UPDATE test_scenarios SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TestScenario extends BaseEntity {

    @Column(nullable = false)
    private UUID assignmentId;

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String httpMethod;

    @Column(nullable = false)
    private String endpoint;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(jakarta.persistence.SqlTypes.JSON)
    private String queryParams;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(jakarta.persistence.SqlTypes.JSON)
    private String requestBody;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(jakarta.persistence.SqlTypes.JSON)
    private String expectedResponseBody;

    private Integer expectedStatus = 200;

    private Integer weight = 1;
}
