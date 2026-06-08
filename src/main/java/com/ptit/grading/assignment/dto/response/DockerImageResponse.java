package com.ptit.grading.assignment.dto.response;

import com.ptit.grading.assignment.model.DockerImageBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DockerImageResponse {
    private UUID id;
    private String name;
    private String image;
    private String platform;
    private String runtimeVersion;

    public static DockerImageResponse from(DockerImageBase imageBase) {
        return DockerImageResponse.builder()
                .id(imageBase.getId())
                .name(imageBase.getName())
                .image(imageBase.getImage())
                .platform(imageBase.getPlatform())
                .runtimeVersion(imageBase.getRuntimeVersion())
                .build();
    }
}
