package com.ptit.grading.assignment.dto.response;

import com.ptit.grading.assignment.model.Assignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse {
    private UUID id;
    private UUID ownerId;
    private String title;
    private String description;
    private boolean published;
    private OffsetDateTime createdAt;

    public static AssignmentResponse from(Assignment assignment) {
        return AssignmentResponse.builder()
                .id(assignment.getId())
                .ownerId(assignment.getOwnerId())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .published(assignment.isPublished())
                .createdAt(assignment.getCreatedAt())
                .build();
    }
}
