package com.ptit.grading.assignment.repository;

import com.ptit.grading.assignment.model.AssignmentDockerImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssignmentDockerImageRepository extends JpaRepository<AssignmentDockerImage, UUID> {
    void deleteByAssignmentId(UUID assignmentId);
}
