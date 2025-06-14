package com.baconbao.JiScrum.service;


import com.baconbao.JiScrum.dto.project.ProjectCreateDTO;
import com.baconbao.JiScrum.dto.project.ProjectDTO;
import com.baconbao.JiScrum.dto.project.ProjectUpdateDTO;

public interface ProjectService {
    ProjectDTO createProject(ProjectCreateDTO dto);
    ProjectDTO updateProject(Integer id, ProjectUpdateDTO dto);
    ProjectDTO getProjectById(Integer id);
    void deleteProject(Integer id);
}
