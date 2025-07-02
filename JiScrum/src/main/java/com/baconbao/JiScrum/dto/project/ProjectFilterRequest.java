package com.baconbao.JiScrum.dto.project;

import com.baconbao.JiScrum.model.Project;
import lombok.Data;

@Data
public class ProjectFilterRequest {
    private String name;
    private Project.ProjectStatus status;
    private Boolean ownerId;
}
