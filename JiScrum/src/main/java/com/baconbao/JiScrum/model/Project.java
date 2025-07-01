package com.baconbao.JiScrum.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @Column(name = "project_id")
    private Integer id;

    @Column(name = "project_name", length = 50, nullable = false)
    private String name;

    @Column(name = "project_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "project_create_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "project_owner_id")
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status", columnDefinition =
            "ENUM('PLANNING','ACTIVE','ON_HOLD','COMPLETED','CANCELLED')")
    private ProjectStatus status;

    @Column(name = "project_start_date")
    private LocalDateTime startDate;

    @Column(name = "project_end_date")
    private LocalDateTime endDate;

    public enum ProjectStatus {
        PLANNING,
        ACTIVE,
        ON_HOLD,
        COMPLETED,
        CANCELLED
    }
}
