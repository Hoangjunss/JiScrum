package com.baconbao.JiScrum.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "projects")
@Data
@Builder
public class Project {

    @Id
    private Integer id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime createAt;

    private Long ownerId;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public enum ProjectStatus {
        PLANNING,
        ACTIVE,
        ON_HOLD,
        COMPLETED,
        CANCELLED
    }
}
