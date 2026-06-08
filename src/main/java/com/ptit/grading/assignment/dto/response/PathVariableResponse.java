package com.ptit.grading.assignment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PathVariableResponse {
    private UUID id;
    private String name;
    private Integer order;
    private String type;
}
