package com.baconbao.JiScrum.service;


import com.baconbao.JiScrum.dto.project.ProjectCreateDTO;
import com.baconbao.JiScrum.dto.project.ProjectDTO;
import com.baconbao.JiScrum.dto.project.ProjectFilterRequest;
import com.baconbao.JiScrum.dto.project.ProjectUpdateDTO;
import com.baconbao.JiScrum.model.Project;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

public interface ProjectService {
    ProjectDTO createProject(ProjectCreateDTO dto) throws BadRequestException;
    ProjectDTO updateProject(Integer id, ProjectUpdateDTO dto);
    ProjectDTO getProjectById(Integer id);
    Project getProjectEntityById(Integer id);
    void deleteProject(Integer id);
    Page<ProjectDTO> filterProjects(ProjectFilterRequest req, int page, int size);
}
