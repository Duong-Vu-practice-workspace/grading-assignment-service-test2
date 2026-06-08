package com.ptit.grading.assignment.repository;

import com.ptit.grading.assignment.model.DockerImageBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DockerImageBaseRepository extends JpaRepository<DockerImageBase, UUID> {
    Optional<DockerImageBase> findByImage(String image);
}
