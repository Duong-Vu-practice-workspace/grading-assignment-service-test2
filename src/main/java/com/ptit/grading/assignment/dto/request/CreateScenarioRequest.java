package com.ptit.grading.assignment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateScenarioRequest {
    private String name;
    private Integer sequenceOrder;
    private String httpMethod;
    private String endpoint;
    private String queryParams;
    private String requestBody;
    private String expectedResponseBody;
    private Integer expectedStatus;
    private Integer weight;
    private List<PathVariableRequest> pathVariables;
}
