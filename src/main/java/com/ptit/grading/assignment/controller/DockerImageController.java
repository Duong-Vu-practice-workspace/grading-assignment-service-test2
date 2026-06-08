package com.ptit.grading.assignment.controller;

import com.ptit.grading.assignment.dto.response.DockerImageResponse;
import com.ptit.grading.assignment.model.DockerImageBase;
import com.ptit.grading.assignment.repository.DockerImageBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/docker-images")
@RequiredArgsConstructor
public class DockerImageController {

    private final DockerImageBaseRepository repository;

    @GetMapping
    public ResponseEntity<List<DockerImageResponse>> list() {
        List<DockerImageResponse> images = repository.findAll()
                .stream()
                .map(DockerImageResponse::from)
                .toList();
        return ResponseEntity.ok(images);
    }

    @PostMapping
    public ResponseEntity<DockerImageResponse> create(@RequestBody DockerImageBase image) {
        DockerImageBase saved = repository.save(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(DockerImageResponse.from(saved));
    }
}
