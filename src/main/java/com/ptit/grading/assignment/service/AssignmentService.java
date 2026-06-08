package com.ptit.grading.assignment.service;

import com.ptit.grading.assignment.dto.request.CreateAssignmentRequest;
import com.ptit.grading.assignment.dto.response.AssignmentResponse;
import com.ptit.grading.assignment.model.Assignment;
import com.ptit.grading.assignment.model.AssignmentDockerImage;
import com.ptit.grading.assignment.repository.AssignmentDockerImageRepository;
import com.ptit.grading.assignment.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentDockerImageRepository assignmentDockerImageRepository;

    @Transactional
    public AssignmentResponse create(CreateAssignmentRequest request, UUID ownerId) {
        Assignment assignment = Assignment.builder()
                .ownerId(ownerId)
                .title(request.getTitle())
                .description(request.getDescription())
                .published(false)
                .build();
        assignment = assignmentRepository.save(assignment);

        // Link docker images
        if (request.getDockerImageBaseIds() != null) {
            for (UUID imageId : request.getDockerImageBaseIds()) {
                assignmentDockerImageRepository.save(
                    AssignmentDockerImage.builder()
                        .assignmentId(assignment.getId())
                        .dockerImageBaseId(imageId)
                        .build()
                );
            }
        }

        return AssignmentResponse.from(assignment);
    }

    @Transactional(readOnly = true)
    public Page<AssignmentResponse> list(Pageable pageable) {
        return assignmentRepository.findAll(pageable).map(AssignmentResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<AssignmentResponse> listPublished(Pageable pageable) {
        return assignmentRepository.findByPublishedTrue(pageable).map(AssignmentResponse::from);
    }

    @Transactional(readOnly = true)
    public AssignmentResponse getById(UUID id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found"));
        return AssignmentResponse.from(assignment);
    }

    @Transactional
    public AssignmentResponse update(UUID id, CreateAssignmentRequest request, UUID ownerId) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!assignment.getOwnerId().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your assignment");
        }

        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment = assignmentRepository.save(assignment);
        return AssignmentResponse.from(assignment);
    }

    @Transactional
    public void publish(UUID id, UUID ownerId) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!assignment.getOwnerId().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your assignment");
        }

        assignment.setPublished(true);
        assignmentRepository.save(assignment);
    }

    @Transactional
    public void delete(UUID id, UUID ownerId) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!assignment.getOwnerId().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your assignment");
        }

        assignmentRepository.deleteById(id);
    }
}
