package com.ptit.grading.assignment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssignmentRequest {
    private String title;
    private String description;
    private List<CreateScenarioRequest> requirements;
    private List<UUID> dockerImageBaseIds;
}
