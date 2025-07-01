package com.baconbao.JiScrum.mapper;

import com.baconbao.JiScrum.dto.issue.IssueCreateDTO;
import com.baconbao.JiScrum.dto.issue.IssueDTO;
import com.baconbao.JiScrum.model.Issue;

import java.time.LocalDateTime;

public class IssueMapper {
    public static Issue toEntity(IssueCreateDTO issueCreateDTO){
        return Issue.builder()
                .title(issueCreateDTO.getTitle())
                .description(issueCreateDTO.getDescription())
                .priority(issueCreateDTO.getPriority())
                .status(Issue.IssueStatus.TO_DO)
                .deadline(issueCreateDTO.getDeadline())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static IssueDTO toDto(Issue issue){
        return IssueDTO.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .priority(issue.getPriority())
                .status(issue.getStatus())
                .deadline(issue.getDeadline())
                .reporterId(issue.getReporter().getId())
                .assigneeId(issue.getAssignee().getId() == null ? null : issue.getAssignee().getId())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt() == null ? null : issue.getUpdatedAt())
                .resolvedAt(issue.getResolvedAt() == null ? null : issue.getResolvedAt())
                .build();
    }
}
