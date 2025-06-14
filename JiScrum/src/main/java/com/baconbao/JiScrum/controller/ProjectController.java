package com.baconbao.JiScrum.controller;


import com.baconbao.JiScrum.dto.APIResponse;
import com.baconbao.JiScrum.dto.project.ProjectCreateDTO;
import com.baconbao.JiScrum.dto.project.ProjectDTO;
import com.baconbao.JiScrum.dto.project.ProjectUpdateDTO;
import com.baconbao.JiScrum.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing projects.
 * Provides endpoints to create, retrieve, update, and delete project resources.
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Creates a new project based on the provided data.
     *
     * @param dto     the project creation request data
     * @param request the HttpServletRequest for retrieving URI path
     * @return a ResponseEntity containing the created ProjectDTO wrapped in APIResponse
     */
    @PostMapping
    public ResponseEntity<APIResponse<ProjectDTO>> createProject(@RequestBody ProjectCreateDTO dto,
                                                                 HttpServletRequest request) {
        log.info("Received request to create a new project");
        ProjectDTO projectDTO = projectService.createProject(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(
                true,
                "Project created successfully",
                projectDTO,
                null,
                request.getRequestURI()
        ));
    }

    /**
     * Retrieves the details of a project by its ID.
     *
     * @param id      the ID of the project to retrieve
     * @param request the HttpServletRequest for retrieving URI path
     * @return a ResponseEntity containing the ProjectDTO wrapped in APIResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProjectDTO>> getProjectById(@PathVariable Integer id,
                                                                  HttpServletRequest request) {
        log.info("Retrieving project with ID: {}", id);
        ProjectDTO projectDTO = projectService.getProjectById(id);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Project retrieved successfully",
                projectDTO,
                null,
                request.getRequestURI()
        ));
    }

    /**
     * Updates an existing project with the given ID using the provided data.
     *
     * @param id      the ID of the project to update
     * @param dto     the data used to update the project
     * @param request the HttpServletRequest for retrieving URI path
     * @return a ResponseEntity containing the updated ProjectDTO wrapped in APIResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProjectDTO>> updateProject(@PathVariable Integer id,
                                                                 @RequestBody ProjectUpdateDTO dto,
                                                                 HttpServletRequest request) {
        log.info("Updating project with ID: {}", id);
        ProjectDTO projectDTO = projectService.updateProject(id, dto);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Project updated successfully",
                projectDTO,
                null,
                request.getRequestURI()
        ));
    }

    /**
     * Deletes the project with the specified ID.
     *
     * @param id      the ID of the project to delete
     * @param request the HttpServletRequest for retrieving URI path
     * @return a ResponseEntity confirming the successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteProject(@PathVariable Integer id,
                                                           HttpServletRequest request) {
        log.info("Deleting project with ID: {}", id);
        projectService.deleteProject(id);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Project deleted successfully",
                null,
                null,
                request.getRequestURI()
        ));
    }
}
