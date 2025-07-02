package com.baconbao.JiScrum.dto.project;


import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createAt;
    private Integer ownerId;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}