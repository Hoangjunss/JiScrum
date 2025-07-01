package com.baconbao.JiScrum.dto.issue;

import com.baconbao.JiScrum.model.Issue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueDTO {
    private Integer id;

    private String title;

    private String description;

    private Issue.IssuePriority priority;

    private Issue.IssueStatus status;

    private Integer assigneeId;

    private Integer reporterId;

    private LocalDateTime deadline;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime resolvedAt;
}
