package com.ptit.grading.assignment.repository;

import com.ptit.grading.assignment.model.PathVariable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PathVariableRepository extends JpaRepository<PathVariable, UUID> {
    List<PathVariable> findByScenarioIdOrderByOrder(UUID scenarioId);
}
