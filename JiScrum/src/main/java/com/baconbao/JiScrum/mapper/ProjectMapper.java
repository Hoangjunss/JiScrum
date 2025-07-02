package com.baconbao.JiScrum.mapper;


import com.baconbao.JiScrum.dto.project.ProjectCreateDTO;
import com.baconbao.JiScrum.dto.project.ProjectDTO;
import com.baconbao.JiScrum.model.Project;

import java.time.LocalDateTime;

/**
 * Mapper class for converting between Project entity and its DTOs.
 */
public class ProjectMapper {

    /**
     * Convert a Project entity to a ProjectDTO.
     *
     * @param project the Project entity
     * @return the corresponding ProjectDTO, or null if input is null
     */
    public static ProjectDTO toDTO(Project project) {
        if (project == null) return null;

        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createAt(LocalDateTime.now())
                .status(project.getStatus().name()) // convert enum to String
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }

    /**
     * Convert a ProjectCreateDTO to a Project entity.
     * This is used when creating a new Project.
     *
     * @param dto the ProjectCreateDTO
     * @return a new Project entity built from the DTO, or null if input is null
     */
    public static Project toEntity(ProjectCreateDTO dto) {
        if (dto == null) return null;

        return Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(Project.ProjectStatus.PLANNING)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .createdAt(LocalDateTime.now())
                .build();
    }


}
