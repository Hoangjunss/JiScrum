package com.baconbao.JiScrum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issue")
public class Issue {
    @Id
    @Column(name = "issue_id", nullable = false)
    private Integer id;

    @Column(name = "issue_title", length = 200)
    private String title;

    @Column(name = "issue_description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "issue_priority",
            columnDefinition = "ENUM('LOWEST','LOW','MEDIUM','HIGH','HIGHEST')")
    private IssuePriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "issue_status",
            columnDefinition = "ENUM('TO_DO','IN_PROGRESS','REVIEW','DONE')")
    private IssueStatus status;

    @ManyToOne
    @JoinColumn(name = "issue_project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "issue_reporter_id")
    private Member reporter;

    @ManyToOne
    @JoinColumn(name = "issue_assignee_id")
    private Member assignee;

    @Column(name = "issue_deadline")
    private LocalDateTime deadline;

    @Column(name = "issue_created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "issue_updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "issue_resolved_at")
    private LocalDateTime resolvedAt;

    public enum IssuePriority {
        LOWEST, LOW, MEDIUM, HIGH, HIGHEST
    }

    public enum IssueStatus {
        TO_DO, IN_PROGRESS, REVIEW, DONE
    }
}
