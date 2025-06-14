package com.baconbao.JiScrum.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "members")
@Data
@Builder
public class Member {

    @Id
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "joined_at", nullable = false)
    private LocalDate joinedAt;

    @Column(name = "left_at")
    private LocalDate leftAt;

    @Column(nullable = false)
    private Boolean status;

    public enum Role {
        PROJECT_MANAGER,
        SCRUM_MASTER,
        PRODUCT_OWNER,
        DEVELOPER,
        TESTER,
        VIEWER
    }
}