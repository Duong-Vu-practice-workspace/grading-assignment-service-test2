package com.ptit.grading.assignment.service;

import com.ptit.grading.assignment.dto.request.CreateScenarioRequest;
import com.ptit.grading.assignment.dto.request.PathVariableRequest;
import com.ptit.grading.assignment.dto.response.PathVariableResponse;
import com.ptit.grading.assignment.dto.response.ScenarioResponse;
import com.ptit.grading.assignment.model.PathVariable;
import com.ptit.grading.assignment.model.TestScenario;
import com.ptit.grading.assignment.repository.PathVariableRepository;
import com.ptit.grading.assignment.repository.TestScenarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScenarioService {

    private final TestScenarioRepository scenarioRepository;
    private final PathVariableRepository pathVariableRepository;

    @Transactional
    public ScenarioResponse create(UUID assignmentId, CreateScenarioRequest request) {
        TestScenario scenario = TestScenario.builder()
                .assignmentId(assignmentId)
                .name(request.getName())
                .sequenceOrder(request.getSequenceOrder() != null ? request.getSequenceOrder() : 0)
                .httpMethod(request.getHttpMethod())
                .endpoint(request.getEndpoint())
                .queryParams(request.getQueryParams())
                .requestBody(request.getRequestBody())
                .expectedResponseBody(request.getExpectedResponseBody())
                .expectedStatus(request.getExpectedStatus() != null ? request.getExpectedStatus() : 200)
                .weight(request.getWeight() != null ? request.getWeight() : 1)
                .build();
        scenario = scenarioRepository.save(scenario);

        // Save path variables
        if (request.getPathVariables() != null) {
            for (PathVariableRequest pvReq : request.getPathVariables()) {
                pathVariableRepository.save(
                    PathVariable.builder()
                        .scenarioId(scenario.getId())
                        .name(pvReq.getName())
                        .order(pvReq.getOrder())
                        .type(pvReq.getType())
                        .build()
                );
            }
        }

        return fromEntity(scenario);
    }

    @Transactional(readOnly = true)
    public List<ScenarioResponse> listByAssignment(UUID assignmentId) {
        return scenarioRepository.findByAssignmentIdOrderBySequenceOrder(assignmentId)
                .stream()
                .map(this::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScenarioResponse getById(UUID assignmentId, UUID scenarioId) {
        TestScenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!scenario.getAssignmentId().equals(assignmentId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Scenario not in this assignment");
        }

        return fromEntity(scenario);
    }

    @Transactional
    public ScenarioResponse update(UUID assignmentId, UUID scenarioId, CreateScenarioRequest request) {
        TestScenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!scenario.getAssignmentId().equals(assignmentId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Scenario not in this assignment");
        }

        scenario.setName(request.getName());
        scenario.setHttpMethod(request.getHttpMethod());
        scenario.setEndpoint(request.getEndpoint());
        scenario.setQueryParams(request.getQueryParams());
        scenario.setRequestBody(request.getRequestBody());
        scenario.setExpectedResponseBody(request.getExpectedResponseBody());
        scenario.setExpectedStatus(request.getExpectedStatus());
        scenario.setWeight(request.getWeight());
        scenario.setSequenceOrder(request.getSequenceOrder());
        scenario = scenarioRepository.save(scenario);

        return fromEntity(scenario);
    }

    @Transactional
    public void delete(UUID assignmentId, UUID scenarioId) {
        TestScenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!scenario.getAssignmentId().equals(assignmentId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Scenario not in this assignment");
        }

        scenarioRepository.deleteById(scenarioId);
    }

    private ScenarioResponse fromEntity(TestScenario scenario) {
        List<PathVariable> pathVars = pathVariableRepository.findByScenarioIdOrderByOrder(scenario.getId());

        ScenarioResponse.ScenarioResponseBuilder builder = ScenarioResponse.from(scenario).toBuilder();

        List<PathVariableResponse> pvResponses = new ArrayList<>();
        for (PathVariable pv : pathVars) {
            pvResponses.add(PathVariableResponse.builder()
                    .id(pv.getId())
                    .name(pv.getName())
                    .order(pv.getOrder())
                    .type(pv.getType())
                    .build());
        }
        builder.pathVariables(pvResponses);

        return builder.build();
    }
}
