package com.ptit.grading.assignment.controller;

import com.ptit.grading.assignment.dto.response.AssignmentResponse;
import com.ptit.grading.assignment.dto.response.ScenarioResponse;
import com.ptit.grading.assignment.service.AssignmentService;
import com.ptit.grading.assignment.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/internal/assignments")
@RequiredArgsConstructor
public class InternalAssignmentController {

    private final AssignmentService assignmentService;
    private final ScenarioService scenarioService;

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignment(@PathVariable UUID id) {
        return ResponseEntity.ok(assignmentService.getById(id));
    }

    @GetMapping("/{id}/scenarios")
    public ResponseEntity<List<ScenarioResponse>> getScenarios(@PathVariable UUID id) {
        return ResponseEntity.ok(scenarioService.listByAssignment(id));
    }
}
