package com.ptit.grading.assignment.controller;

import com.ptit.grading.assignment.dto.request.CreateScenarioRequest;
import com.ptit.grading.assignment.dto.response.ScenarioResponse;
import com.ptit.grading.assignment.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments/{assignmentId}/scenarios")
@RequiredArgsConstructor
public class ScenarioController {

    private final ScenarioService scenarioService;

    @PostMapping
    public ResponseEntity<ScenarioResponse> create(
            @PathVariable UUID assignmentId,
            @RequestBody CreateScenarioRequest request) {
        ScenarioResponse response = scenarioService.create(assignmentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ScenarioResponse>> list(@PathVariable UUID assignmentId) {
        return ResponseEntity.ok(scenarioService.listByAssignment(assignmentId));
    }

    @GetMapping("/{scenarioId}")
    public ResponseEntity<ScenarioResponse> getById(
            @PathVariable UUID assignmentId,
            @PathVariable UUID scenarioId) {
        return ResponseEntity.ok(scenarioService.getById(assignmentId, scenarioId));
    }

    @PutMapping("/{scenarioId}")
    public ResponseEntity<ScenarioResponse> update(
            @PathVariable UUID assignmentId,
            @PathVariable UUID scenarioId,
            @RequestBody CreateScenarioRequest request) {
        return ResponseEntity.ok(scenarioService.update(assignmentId, scenarioId, request));
    }

    @DeleteMapping("/{scenarioId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID assignmentId,
            @PathVariable UUID scenarioId) {
        scenarioService.delete(assignmentId, scenarioId);
        return ResponseEntity.noContent().build();
    }
}
