package com.ptit.grading.assignment.model;

import com.ptit.grading.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "docker_image_bases")
@SQLDelete(sql = "UPDATE docker_image_bases SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DockerImageBase extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String image;

    @Column(nullable = false)
    private String platform;

    private String runtimeVersion;

    @Column(nullable = false)
    private boolean defaultForPlatform = false;
}
