package com.baconbao.JiScrum.dto.project;


import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
public class ProjectDTO {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createAt;
    private Long ownerId;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}