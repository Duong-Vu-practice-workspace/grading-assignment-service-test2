package com.ptit.grading.assignment.repository;

import com.ptit.grading.assignment.model.TestScenario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestScenarioRepository extends JpaRepository<TestScenario, UUID> {
    List<TestScenario> findByAssignmentIdOrderBySequenceOrder(UUID assignmentId);
}
