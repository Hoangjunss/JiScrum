package com.baconbao.JiScrum.repository;

import com.baconbao.JiScrum.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
}
