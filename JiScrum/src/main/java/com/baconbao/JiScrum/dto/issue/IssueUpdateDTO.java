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
public class IssueUpdateDTO {
    private String title;

    private String description;

    private Issue.IssuePriority priority;

    private Issue.IssueStatus status;

    private Integer assigneeId;

    private LocalDateTime deadline;

    private LocalDateTime resolvedAt;
}
