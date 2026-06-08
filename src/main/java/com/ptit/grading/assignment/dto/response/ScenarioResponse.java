package com.ptit.grading.assignment.dto.response;

import com.ptit.grading.assignment.model.TestScenario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScenarioResponse {
    private UUID id;
    private UUID assignmentId;
    private Integer sequenceOrder;
    private String name;
    private String httpMethod;
    private String endpoint;
    private String queryParams;
    private String requestBody;
    private String expectedResponseBody;
    private Integer expectedStatus;
    private Integer weight;
    private List<PathVariableResponse> pathVariables;

    public static ScenarioResponse from(TestScenario scenario) {
        return ScenarioResponse.builder()
                .id(scenario.getId())
                .assignmentId(scenario.getAssignmentId())
                .sequenceOrder(scenario.getSequenceOrder())
                .name(scenario.getName())
                .httpMethod(scenario.getHttpMethod())
                .endpoint(scenario.getEndpoint())
                .queryParams(scenario.getQueryParams())
                .requestBody(scenario.getRequestBody())
                .expectedResponseBody(scenario.getExpectedResponseBody())
                .expectedStatus(scenario.getExpectedStatus())
                .weight(scenario.getWeight())
                .build();
    }
}
