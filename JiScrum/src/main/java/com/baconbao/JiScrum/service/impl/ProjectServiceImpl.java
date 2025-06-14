package com.baconbao.JiScrum.service.impl;


import com.baconbao.JiScrum.dto.project.ProjectCreateDTO;
import com.baconbao.JiScrum.dto.project.ProjectDTO;
import com.baconbao.JiScrum.dto.project.ProjectUpdateDTO;
import com.baconbao.JiScrum.mapper.ProjectMapper;
import com.baconbao.JiScrum.model.Project;
import com.baconbao.JiScrum.repository.ProjectRepository;
import com.baconbao.JiScrum.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * Creates a new project using the provided creation DTO.
     * Validates required fields before mapping and saving to the database.
     *
     * @param dto the DTO containing data to create the project
     * @return the created project as a DTO
     * @throws ResponseStatusException if required fields are missing
     */
    @Override
    public ProjectDTO createProject(ProjectCreateDTO dto) {
        log.info("Creating new project with name: {}", dto.getName());

        // Validate required fields (you can expand this list based on your requirements)
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            log.error("Project name is required but was null or empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project name must not be null or empty");
        }

        if (dto.getOwnerId() == null) {
            log.error("Owner ID is required but was null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner ID must not be null");
        }

        // Map DTO to entity
        Project project = ProjectMapper.toEntity(dto);
        project.setCreateAt(LocalDateTime.now());

        // Save to database
        Project saved = projectRepository.save(project);
        log.debug("Project saved successfully with ID: {}", saved.getId());

        // Return as DTO
        return ProjectMapper.toDTO(saved);
    }

    /**
     * Updates an existing project by its ID using the provided update DTO.
     * Only non-null fields in the DTO will be applied to the project entity.
     *
     * @param id  the ID of the project to update
     * @param dto the DTO containing updated project data
     * @return the updated project as a DTO
     * @throws ResponseStatusException if the project is not found
     */
    @Override
    public ProjectDTO updateProject(Integer id, ProjectUpdateDTO dto) {
        log.info("Updating project with ID: {}", id);

        // Retrieve the project by ID or throw 404 if not found
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Project with ID {} not found for update", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + id);
                });

        // Update fields only if new values are provided in the DTO
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

        // Save the updated project back to the database
        Project updated = projectRepository.save(project);
        log.debug("Project with ID {} updated successfully", id);

        // Return the updated entity as a DTO
        return ProjectMapper.toDTO(updated);
    }


    /**
     * Retrieves a project by its unique ID.
     *
     * @param id the ID of the project
     * @return the project converted to a DTO
     * @throws ResponseStatusException if no project is found with the given ID
     */
    @Override
    public ProjectDTO getProjectById(Integer id) {
        log.info("Retrieving project with ID: {}", id);

        // Find the project or throw 404 error if not found
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Project with ID {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + id);
                });

        // Convert entity to DTO and return
        return ProjectMapper.toDTO(project);
    }


    /**
     * Deletes a project by its unique ID.
     *
     * @param id the ID of the project to delete
     * @throws ResponseStatusException if the project does not exist
     */
    @Override
    public void deleteProject(Integer id) {
        log.info("Deleting project with ID: {}", id);

        // Verify existence before deletion
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Project with ID {} not found for deletion", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + id);
                });

        // Perform deletion
        projectRepository.delete(project);
        log.info("Project with ID {} deleted successfully", id);
    }

}