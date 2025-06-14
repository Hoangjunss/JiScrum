package com.baconbao.JiScrum.service.impl;


import com.baconbao.JiScrum.dto.project.ProjectCreateDTO;
import com.baconbao.JiScrum.dto.project.ProjectDTO;
import com.baconbao.JiScrum.dto.project.ProjectUpdateDTO;
import com.baconbao.JiScrum.exception.ResourceNotFoundException;
import com.baconbao.JiScrum.mapper.ProjectMapper;
import com.baconbao.JiScrum.model.Project;
import com.baconbao.JiScrum.repository.ProjectRepository;
import com.baconbao.JiScrum.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

/**
 * Implementation of the ProjectService interface.
 * Handles business logic related to Project operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    /**
     * Create a new project using the provided DTO.
     * Validates input and persists the entity.
     *
     * @param dto the DTO containing data to create the project
     * @return the created project as a DTO
     * @throws BadRequestException if required fields are missing or invalid
     */
    @Override
    public ProjectDTO createProject(ProjectCreateDTO dto) throws BadRequestException {
        log.info("Creating new project with name: {}", dto.getName());

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            log.error("Project name is required but was null or empty");
            throw new BadRequestException("Project name must not be null or empty");
        }

        if (dto.getOwnerId() == null) {
            log.error("Owner ID is required but was null");
            throw new BadRequestException("Owner ID must not be null");
        }

        Project project = ProjectMapper.toEntity(dto);
        project.setCreateAt(LocalDateTime.now());

        Project saved = projectRepository.save(project);
        log.debug("Project saved successfully with ID: {}", saved.getId());

        return ProjectMapper.toDTO(saved);
    }

    /**
     * Update an existing project by ID.
     * Applies only the fields that are provided in the update DTO.
     *
     * @param id  the ID of the project to update
     * @param dto the DTO containing updated values
     * @return the updated project as a DTO
     * @throws ResourceNotFoundException if the project with given ID does not exist
     */
    @Override
    public ProjectDTO updateProject(Integer id, ProjectUpdateDTO dto) {
        log.info("Updating project with ID: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Project with ID {} not found for update", id);
                    return new ResourceNotFoundException("Project not found with id: " + id);
                });

        if (dto.getName() != null) {
            project.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            project.setDescription(dto.getDescription());
        }
        if (dto.getOwnerId() != null) {
            project.setOwnerId(dto.getOwnerId());
        }
        if (dto.getStatus() != null) {
            project.setStatus(dto.getStatus());
        }
        if (dto.getStartDate() != null) {
            project.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            project.setEndDate(dto.getEndDate());
        }

        Project updated = projectRepository.save(project);
        log.debug("Project with ID {} updated successfully", id);

        return ProjectMapper.toDTO(updated);
    }

    /**
     * Retrieve a project by ID.
     *
     * @param id the ID of the project to retrieve
     * @return the project as a DTO
     * @throws ResourceNotFoundException if no project exists with the given ID
     */
    @Override
    public ProjectDTO getProjectById(Integer id) {
        log.info("Retrieving project with ID: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Project with ID {} not found", id);
                    return new ResourceNotFoundException("Project not found with id: " + id);
                });

        return ProjectMapper.toDTO(project);
    }

    /**
     * Delete a project by ID.
     *
     * @param id the ID of the project to delete
     * @throws ResourceNotFoundException if no project exists with the given ID
     */
    @Override
    public void deleteProject(Integer id) {
        log.info("Deleting project with ID: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Project with ID {} not found for deletion", id);
                    return new ResourceNotFoundException("Project not found with id: " + id);
                });

        projectRepository.delete(project);
        log.info("Project with ID {} deleted successfully", id);
    }
}
