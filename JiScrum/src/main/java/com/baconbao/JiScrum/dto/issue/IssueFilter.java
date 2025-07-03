package com.baconbao.JiScrum.dto.issue;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueFilter {
    private String title;
    private String priority;
    private String status;
    private String projectId;
    private Boolean reporterId;
    private LocalDateTime deadlineFrom;
    private LocalDateTime deadlineTo;
}
