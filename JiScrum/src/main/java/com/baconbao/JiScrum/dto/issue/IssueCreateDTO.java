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
public class IssueCreateDTO {
    private String title;

    private String description;

    private Issue.IssuePriority priority;

    private Integer projectId;

    private Integer reporterId;

    private Integer assigneeId;

    private LocalDateTime deadline;
}
