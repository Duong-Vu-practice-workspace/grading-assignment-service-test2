package com.ptit.grading.assignment.repository;

import com.ptit.grading.assignment.model.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    Page<Assignment> findByPublishedTrue(Pageable pageable);
}
