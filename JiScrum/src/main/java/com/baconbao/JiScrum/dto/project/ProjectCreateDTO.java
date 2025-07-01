package com.baconbao.JiScrum.dto.project;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectCreateDTO {
    private String name;
    private String description;
    private Long ownerId;
    private LocalDateTime startDate;
}