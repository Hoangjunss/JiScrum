package com.baconbao.JiScrum.dto.project;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProjectCreateDTO {
    private String name;
    private String description;
    private Long ownerId;
    private LocalDateTime startDate;
}