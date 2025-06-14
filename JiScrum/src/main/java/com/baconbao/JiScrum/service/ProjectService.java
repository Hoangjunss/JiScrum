package com.baconbao.JiScrum.service;


import com.baconbao.JiScrum.dto.project.ProjectCreateDTO;
import com.baconbao.JiScrum.dto.project.ProjectDTO;
import com.baconbao.JiScrum.dto.project.ProjectUpdateDTO;
import org.apache.coyote.BadRequestException;

public interface ProjectService {
    ProjectDTO createProject(ProjectCreateDTO dto) throws BadRequestException;
    ProjectDTO updateProject(Integer id, ProjectUpdateDTO dto);
    ProjectDTO getProjectById(Integer id);
    void deleteProject(Integer id);
}
