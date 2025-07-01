package com.baconbao.JiScrum.dto.project;

import com.baconbao.JiScrum.model.Project;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectUpdateDTO {
    private String name;
    private String description;
    private Long ownerId;
    private Project.ProjectStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}