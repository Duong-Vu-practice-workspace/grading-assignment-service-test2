package com.ptit.grading.assignment.controller;

import com.ptit.grading.assignment.dto.request.CreateAssignmentRequest;
import com.ptit.grading.assignment.dto.response.AssignmentResponse;
import com.ptit.grading.assignment.service.AssignmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<AssignmentResponse> create(
            @RequestBody CreateAssignmentRequest request,
            @RequestHeader("X-User-Id") UUID userId) {
        AssignmentResponse response = assignmentService.create(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AssignmentResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(assignmentService.list(pageable));
    }

    @GetMapping("/published")
    public ResponseEntity<Page<AssignmentResponse>> listPublished(Pageable pageable) {
        return ResponseEntity.ok(assignmentService.listPublished(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(assignmentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentResponse> update(
            @PathVariable UUID id,
            @RequestBody CreateAssignmentRequest request,
            @RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(assignmentService.update(id, request, userId));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<Void> publish(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") UUID userId) {
        assignmentService.publish(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") UUID userId) {
        assignmentService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
